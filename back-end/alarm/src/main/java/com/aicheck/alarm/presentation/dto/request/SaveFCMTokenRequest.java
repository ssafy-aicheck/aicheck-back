package com.aicheck.alarm.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record SaveFCMTokenRequest(
	@NotNull(message = "memberId 값이 없습니다.")
	Long memberId,
	@NotNull(message = "token 값이 없습니다.")
	String token
) {
}
