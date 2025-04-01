package com.aicheck.alarm.application.dto;

import com.aicheck.alarm.domain.Type;

public record AlarmEventMessage(
	Long memberId,
	String title,
	String body,
	Type type,
	Long endPointId
) {
}
