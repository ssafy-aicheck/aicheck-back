package com.aicheck.business.domain.allowance.application.service;

import com.aicheck.business.domain.allowance.dto.AllowanceIncreaseDecisionRequest;
import com.aicheck.business.domain.allowance.dto.AllowanceIncreaseRequestDetailResponse;
import com.aicheck.business.domain.allowance.dto.CreateAllowanceIncreaseRequest;
import com.aicheck.business.domain.allowance.entity.AllowanceIncreaseRequest;
import com.aicheck.business.domain.allowance.presentation.dto.request.SaveAllowanceRequest;

public interface AllowanceService {
    void requestIncrease(Long childId, CreateAllowanceIncreaseRequest request);

    void respondToRequest(Long requestId, AllowanceIncreaseDecisionRequest request);

    AllowanceIncreaseRequestDetailResponse getAllowanceIncreaseRequestDetail(Long requestId);

    Long saveAllowanceRequest(SaveAllowanceRequest request);
}
