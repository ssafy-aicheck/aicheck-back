package com.aicheck.business.domain.allowance.dto;

import java.time.LocalDateTime;

import com.aicheck.business.domain.allowance.entity.AllowanceIncreaseRequest;
import com.querydsl.core.annotations.QueryProjection;

public record AllowanceIncreaseRequestDto(
	Long id,
	Long parentId,
	Long childId,
	String childName,
	String image,
	Integer beforeAmount,
	Integer afterAmount,
	String reportId,
	AllowanceIncreaseRequest.Status status,
	String summary,
	String description,
	LocalDateTime createdAt
) {

	@QueryProjection
	public AllowanceIncreaseRequestDto{
	}
}
