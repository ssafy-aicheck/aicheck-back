package com.aicheck.business.domain.allowance;

public interface AllowanceService {
    void requestIncrease(Long childId, CreateAllowanceIncreaseRequest request);
}
