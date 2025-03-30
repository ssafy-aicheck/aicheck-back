package com.aicheck.business.domain.allowance;

import com.aicheck.business.domain.allowance.AllowanceIncreaseRequest.Status;
import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.global.error.BusinessErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceService {

    private final AllowanceIncreaseRequestRepository allowanceIncreaseRequestRepository;
    private final BatchClient batchClient;
    private final MemberRepository memberRepository;

    @Override
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
                .beforeAmount(childScheduleResponse.getAmount())
                .afterAmount(childScheduleResponse.getAmount() + request.getIncreaseAmount())
                .description(request.getReason())
                .build();
        allowanceIncreaseRequestRepository.save(allowanceIncreaseRequest);
    }

}
