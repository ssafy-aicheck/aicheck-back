package com.aicheck.business.domain.allowance.application.service;

import com.aicheck.business.domain.allowance.application.client.BatchClient;
import com.aicheck.business.domain.allowance.application.client.dto.ChildScheduleResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceService {

    private final AllowanceIncreaseRequestRepository allowanceIncreaseRequestRepository;
    private final BatchClient batchClient;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void requestIncrease(Long childId, CreateAllowanceIncreaseRequest request) {
        Member member = memberRepository.findById(childId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));

        if (allowanceIncreaseRequestRepository.findByChildIdAndStatus(childId, Status.WAITING) != null) {
            throw new BusinessException(BusinessErrorCodes.ALLOWANCE_REQUEST_ALREADY_EXIST);
        }

        ChildScheduleResponse childScheduleResponse = batchClient.getChildSchedule(childId);
        AllowanceIncreaseRequest allowanceIncreaseRequest = AllowanceIncreaseRequest.builder()
                .childId(childId)
                .parentId(member.getManagerId())
                .reportId(request.getReportId())
                .beforeAmount(childScheduleResponse.getAmount())
                .afterAmount(childScheduleResponse.getAmount() + request.getIncreaseAmount())
                .description(request.getReason())
                .build();
        allowanceIncreaseRequestRepository.save(allowanceIncreaseRequest);
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

        if (decision.equals(Status.ACCEPTED)) {
            allowanceIncreaseRequest.accept();
            /*
            TODO : 자녀에게 승인 푸시알림, 정기 송금 금액 변경
             */
        }
        if (decision.equals(Status.REJECTED)) {
            allowanceIncreaseRequest.reject();
            /*
            TODO : 자녀에게 거절 푸시알림
             */
        }
    }

    @Override
    public AllowanceIncreaseRequestDetailResponse getAllowanceIncreaseRequestDetail(Long requestId) {
        AllowanceIncreaseRequest allowanceIncreaseRequest = allowanceIncreaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.ALLOWANCE_REQUEST_NOT_FOUND));

        Member child = memberRepository.findById(allowanceIncreaseRequest.getChildId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));

        return AllowanceIncreaseRequestDetailResponse.from(child, allowanceIncreaseRequest);
    }

}
