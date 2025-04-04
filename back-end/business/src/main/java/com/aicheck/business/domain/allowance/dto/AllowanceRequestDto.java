package com.aicheck.business.domain.allowance.dto;

import java.time.LocalDateTime;

import com.aicheck.business.domain.allowance.entity.AllowanceRequest;
import com.querydsl.core.annotations.QueryProjection;

public record AllowanceRequestDto(
	Long id,
	Long parentId,
	Long childId,
	String childName,
	String image,
	Integer amount,
	String firstCategoryName,
	String secondCategoryName,
	AllowanceRequest.Status status,
	String description,
	LocalDateTime createdAt
) {

	@QueryProjection
	public AllowanceRequestDto {
	}
}