package com.aicheck.business.domain.allowance.presentation.dto.request;

public record SaveAllowanceRequest(

	Long childId,
	Long parentId,
	Integer amount,
	String firstCategoryName,
	String secondCategoryName,
	String description
) {
}
