package com.aicheck.business.domain.allowance.application.service;

import static com.aicheck.business.domain.allowance.entity.AllowanceRequest.Status.ACCEPTED;
import static com.aicheck.business.domain.auth.domain.entity.MemberType.PARENT;
import static com.aicheck.business.global.error.BusinessErrorCodes.ALREADY_DECIDED_ALLOWANCE_REQUEST;
import static com.aicheck.business.global.error.BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND;
import static com.aicheck.business.global.error.BusinessErrorCodes.NOT_FOUND_ALLOWANCE_REQUEST;
import static com.aicheck.business.global.error.BusinessErrorCodes.UNAUTHORIZED_UPDATE_ALLOWANCE_REQUEST_STATUS;
import static com.aicheck.business.global.infrastructure.event.Type.ALLOWANCE_RESPONSE;

import com.aicheck.business.domain.allowance.dto.AllowanceRequestDto;
import com.aicheck.business.domain.allowance.entity.AllowanceRequest;
import com.aicheck.business.domain.allowance.infrastructure.AllowanceQueryDslRepository;
import com.aicheck.business.domain.allowance.presentation.dto.request.SaveAllowanceRequest;
import com.aicheck.business.domain.allowance.presentation.dto.request.UpdateAllowanceRequestResponse;
import com.aicheck.business.domain.allowance.presentation.dto.response.AllowanceResponse;
import com.aicheck.business.domain.allowance.repository.AllowanceRequestRepository;
import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.entity.MemberType;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.transfer.application.TransferService;
import com.aicheck.business.domain.transfer.presentation.dto.TransferRequest;
import com.aicheck.business.global.infrastructure.event.AlarmEventProducer;
import com.aicheck.business.global.infrastructure.event.dto.request.AlarmEventMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AllowanceServiceImpl implements AllowanceService {

    private final AllowanceRequestRepository allowanceRequestRepository;
    private final AllowanceQueryDslRepository allowanceQueryDslRepository;
    private final MemberRepository memberRepository;
    private final AlarmEventProducer alarmEventProducer;
    private final TransferService transferService;

    @Transactional
    @Override
    public Long saveAllowanceRequest(final SaveAllowanceRequest request) {
        final AllowanceRequest allowanceRequest = AllowanceRequest.builder()
                .parent(Member.withId(request.parentId()))
                .child(Member.withId(request.childId()))
                .amount(request.amount())
                .firstCategoryName(request.firstCategoryName())
                .secondCategoryName(request.secondCategoryName())
                .description(request.description())
                .build();

        allowanceRequestRepository.save(allowanceRequest);
        return allowanceRequest.getId();
    }

    @Override
    public List<AllowanceResponse> getAllowanceRequests(Long memberId) {
        final MemberType memberType = memberRepository.findMemberTypeById(memberId)
                .orElseThrow(() -> new BusinessException(BUSINESS_MEMBER_NOT_FOUND));

        if (memberType.equals(PARENT)) {
            return allowanceQueryDslRepository.findAllByParent(memberId);
        } else {
            return allowanceQueryDslRepository.findAllByChild(memberId);
        }
    }

    @Transactional
    @Override
    public void updateAllowanceRequestResponse(Long parentId,
                                               UpdateAllowanceRequestResponse updateAllowanceRequestResponse) {
        final AllowanceRequest allowanceRequest = allowanceRequestRepository.findById(
                        updateAllowanceRequestResponse.id())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_ALLOWANCE_REQUEST));

        if (!parentId.equals(allowanceRequest.getParent().getId())) {
            throw new BusinessException(UNAUTHORIZED_UPDATE_ALLOWANCE_REQUEST_STATUS);
        }

        if (allowanceRequest.isAlreadyDecided()) {
            throw new BusinessException(ALREADY_DECIDED_ALLOWANCE_REQUEST);
        }

		if (updateAllowanceRequestResponse.status().equals(ACCEPTED)) {
			allowanceRequest.accept();
			TransferRequest transferRequest = TransferRequest.builder()
					.receiverAccountNo(allowanceRequest.getChild().getAccountNo())
					.amount(Long.valueOf(allowanceRequest.getAmount()))
					.build();
			transferService.executeTransfer(parentId, transferRequest);
		} else {
			allowanceRequest.reject();
		}

        alarmEventProducer.sendEvent(AlarmEventMessage.of(
                allowanceRequest.getChild().getId(),
                getResponseTitle(updateAllowanceRequestResponse.status()),
                getResponseBody(updateAllowanceRequestResponse.status()),
                ALLOWANCE_RESPONSE,
                allowanceRequest.getId()
        ));
    }

    @Override
    public AllowanceRequestDto getAllowanceRequest(Long id) {
        return allowanceQueryDslRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_ALLOWANCE_REQUEST));
    }

    private String getResponseTitle(AllowanceRequest.Status status) {
        return String.format("부모님이 용돈 요청을 %s했습니다.",
                status.equals(AllowanceRequest.Status.ACCEPTED) ? "수락" : "거절");
    }

    private String getResponseBody(AllowanceRequest.Status status) {
        return String.format("부모님이 용돈 요청을 %s했습니다.",
                status.equals(AllowanceRequest.Status.ACCEPTED) ? "수락" : "거절");
    }
}