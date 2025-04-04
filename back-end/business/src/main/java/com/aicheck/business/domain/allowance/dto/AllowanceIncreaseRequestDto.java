package com.aicheck.business.domain.allowance.dto;

import java.time.LocalDateTime;

public record AllowanceIncreaseRequestDto(
	Long id,
	String type,
	String status,
	Long reportId,
	Long childId,
	String childName,
	String image,
	Integer prevAmount,
	Integer afterAmount,
	String description,
	LocalDateTime createdAt
) {
}
