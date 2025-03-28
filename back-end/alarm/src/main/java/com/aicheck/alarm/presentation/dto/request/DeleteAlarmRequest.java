package com.aicheck.alarm.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record DeleteAlarmRequest(
	@NotNull(message = "alarmId 값이 없습니다.")
	Long alarmId
) {
}
