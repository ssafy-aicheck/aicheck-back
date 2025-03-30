package com.aicheck.business.domain.allowance.application.service;

import com.aicheck.business.domain.allowance.dto.AllowanceIncreaseDecisionRequest;
import com.aicheck.business.domain.allowance.dto.CreateAllowanceIncreaseRequest;

public interface AllowanceService {
    void requestIncrease(Long childId, CreateAllowanceIncreaseRequest request);

    void respondToRequest(Long requestId, AllowanceIncreaseDecisionRequest request);
}
