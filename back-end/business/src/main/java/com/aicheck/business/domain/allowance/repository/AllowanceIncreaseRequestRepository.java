package com.aicheck.business.domain.allowance.repository;

import com.aicheck.business.domain.allowance.entity.AllowanceIncreaseRequest;
import com.aicheck.business.domain.allowance.entity.AllowanceIncreaseRequest.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowanceIncreaseRequestRepository extends JpaRepository<AllowanceIncreaseRequest, Long> {
    AllowanceIncreaseRequest findByChildIdAndStatus(Long childId, Status status);
}
