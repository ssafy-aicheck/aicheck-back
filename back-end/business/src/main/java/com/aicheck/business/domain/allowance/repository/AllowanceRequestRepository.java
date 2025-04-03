package com.aicheck.business.domain.allowance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aicheck.business.domain.allowance.entity.AllowanceRequest;

public interface AllowanceRequestRepository extends JpaRepository<AllowanceRequest, Long> {
}
