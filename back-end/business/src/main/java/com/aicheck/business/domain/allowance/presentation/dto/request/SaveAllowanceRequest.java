package com.aicheck.business.domain.allowance.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record SaveAllowanceRequest(
	@NotNull(message = "childId가 없습니다.")
	Long childId,
	@NotNull(message = "parentId가 없습니다.")
	Long parentId,
	@NotNull(message = "amount가 없습니다.")
	Integer amount,
	@NotNull(message = "firstCategoryName가 없습니다.")
	String firstCategoryName,
	@NotNull(message = "secondCategoryName가 없습니다.")
	String secondCategoryName,
	@NotNull(message = "description이 없습니다.")
	String description
) {
}
