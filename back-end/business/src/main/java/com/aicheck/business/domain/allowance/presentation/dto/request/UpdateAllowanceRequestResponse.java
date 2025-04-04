package com.aicheck.business.domain.allowance.presentation.dto.request;

import com.aicheck.business.domain.allowance.entity.AllowanceRequest;

import jakarta.validation.constraints.NotNull;

public record UpdateAllowanceRequestResponse(
	@NotNull(message = "id가 없습니다.")
	Long id,
	@NotNull(message = "status가 없습니다.")
	AllowanceRequest.Status status
) {
}
