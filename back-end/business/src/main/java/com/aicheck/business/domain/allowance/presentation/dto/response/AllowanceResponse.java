package com.aicheck.business.domain.allowance.presentation.dto.response;

import java.time.LocalDateTime;

import com.aicheck.business.domain.allowance.entity.AllowanceRequest;

public abstract class AllowanceResponse {
	private Long id;
	private String type;
	private AllowanceRequest.Status status;
	private String childName;
	private Integer amount;
	private String description;
	private LocalDateTime createdAt;
}
