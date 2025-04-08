package com.aicheck.business.domain.allowance.application.service;

import static com.aicheck.business.domain.allowance.entity.AllowanceIncreaseRequest.Status.ACCEPTED;
import static com.aicheck.business.domain.allowance.entity.AllowanceIncreaseRequest.Status.REJECTED;
import static com.aicheck.business.domain.allowance.entity.AllowanceIncreaseRequest.Status.WAITING;
import static com.aicheck.business.global.infrastructure.event.Type.ALLOWANCE_INCREASE;
import static com.aicheck.business.global.infrastructure.event.Type.ALLOWANCE_INCREASE_RESPONSE;

import com.aicheck.business.domain.allowance.application.client.BatchClient;
import com.aicheck.business.domain.allowance.application.client.dto.ChildScheduleResponse;
import com.aicheck.business.domain.allowance.application.client.dto.UpdateAllowanceFeignRequest;
import com.aicheck.business.domain.allowance.dto.AllowanceIncreaseDecisionRequest;
import com.aicheck.business.domain.allowance.dto.AllowanceIncreaseRequestDetailResponse;
import com.aicheck.business.domain.allowance.dto.CreateAllowanceIncreaseRequest;
import com.aicheck.business.domain.allowance.entity.AllowanceIncreaseRequest;
import com.aicheck.business.domain.allowance.entity.AllowanceIncreaseRequest.Status;
import com.aicheck.business.domain.allowance.repository.AllowanceIncreaseRequestRepository;
import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.global.error.BusinessErrorCodes;
import com.aicheck.business.global.infrastructure.event.AlarmEventProducer;
import com.aicheck.business.global.infrastructure.event.dto.request.AlarmEventMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AllowanceIncreaseServiceImpl implements AllowanceIncreaseService {

    private final AllowanceIncreaseRequestRepository allowanceIncreaseRequestRepository;
    private final BatchClient batchClient;
    private final MemberRepository memberRepository;
    private final AlarmEventProducer alarmEventProducer;

    @Override
    @Transactional
    public void requestIncrease(Long childId, CreateAllowanceIncreaseRequest request) {
        Member member = memberRepository.findById(childId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));

        if (allowanceIncreaseRequestRepository.findByChildIdAndStatus(childId, WAITING) != null) {
            throw new BusinessException(BusinessErrorCodes.ALLOWANCE_REQUEST_ALREADY_EXIST);
        }

        ChildScheduleResponse childScheduleResponse = batchClient.getChildSchedule(childId);
        AllowanceIncreaseRequest allowanceIncreaseRequest = AllowanceIncreaseRequest.builder()
                .child(member)
                .parent(Member.withId(member.getManagerId()))
                .reportId(request.getReportId())
                .beforeAmount(childScheduleResponse.getAmount())
                .afterAmount(childScheduleResponse.getAmount() + request.getIncreaseAmount())
                .description(request.getReason())
                .build();
        allowanceIncreaseRequestRepository.save(allowanceIncreaseRequest);
        alarmEventProducer.sendEvent(AlarmEventMessage.of(
                member.getManagerId(),
                getRequestTitle(member.getName()),
                getRequestBody(
                        member.getName(),
                        allowanceIncreaseRequest.getBeforeAmount(),
                        allowanceIncreaseRequest.getAfterAmount()),
                ALLOWANCE_INCREASE,
                allowanceIncreaseRequest.getId()
        ));
    }

    @Override
    @Transactional
    public void respondToRequest(Long requestId, AllowanceIncreaseDecisionRequest request) {
        AllowanceIncreaseRequest allowanceIncreaseRequest = allowanceIncreaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.ALLOWANCE_REQUEST_NOT_FOUND));
        if (allowanceIncreaseRequest.isAlreadyDecided()) {
            throw new BusinessException(BusinessErrorCodes.ALREADY_DECIDED_ALLOWANCE_REQUEST);
        }
        Status decision;
        try {
            decision = Status.valueOf(request.getStatus());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(BusinessErrorCodes.INVALID_RESPOND_STATUS);
        }

        if (decision.equals(ACCEPTED)) {
            allowanceIncreaseRequest.accept();
            batchClient.updateAllowanceByChildId(allowanceIncreaseRequest.getChild().getId(),
                    UpdateAllowanceFeignRequest.builder()
                            .amount(allowanceIncreaseRequest.getAfterAmount())
                            .build());

            alarmEventProducer.sendEvent(AlarmEventMessage.of(
                    allowanceIncreaseRequest.getChild().getId(),
                    getResponseTitle(ACCEPTED),
                    getResponseBody(allowanceIncreaseRequest, ACCEPTED),
                    ALLOWANCE_INCREASE_RESPONSE,
                    allowanceIncreaseRequest.getId()));
        }
        if (decision.equals(REJECTED)) {
            allowanceIncreaseRequest.reject();

            alarmEventProducer.sendEvent(AlarmEventMessage.of(
                    allowanceIncreaseRequest.getChild().getId(),
                    getResponseTitle(REJECTED),
                    getResponseBody(allowanceIncreaseRequest, REJECTED),
                    ALLOWANCE_INCREASE_RESPONSE,
                    allowanceIncreaseRequest.getId()));
        }
    }

    @Override
    public AllowanceIncreaseRequestDetailResponse getAllowanceIncreaseRequestDetail(Long requestId) {
        AllowanceIncreaseRequest allowanceIncreaseRequest = allowanceIncreaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.ALLOWANCE_REQUEST_NOT_FOUND));

        Member child = memberRepository.findById(allowanceIncreaseRequest.getChild().getId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));

        return AllowanceIncreaseRequestDetailResponse.from(child, allowanceIncreaseRequest);
    }

    private String getResponseTitle(Status status) {
        return String.format("부모님이 정기 용돈 인상 요청을 %s했습니다.", status.equals(ACCEPTED) ? "수락" : "거절");
    }

    private String getResponseBody(AllowanceIncreaseRequest request, Status status) {
        return String.format("부모님이 %,d원에서 %,d원으로의 정기 용돈 인상 요청을 %s했습니다.",
                request.getBeforeAmount(), request.getAfterAmount(), status.equals(ACCEPTED) ? "수락" : "거절");
    }

    private String getRequestTitle(String name) {
        return String.format("%s님이 정기 용돈 인상을 요청했습니다.", name);
    }

    private String getRequestBody(String name, Integer beforeAmount, Integer afterAmount) {
        return String.format("%s님이 %,d원에서 %,d원으로의 정기 용돈 인상을 요청했습니다.",
                name, beforeAmount, afterAmount);
    }
}
