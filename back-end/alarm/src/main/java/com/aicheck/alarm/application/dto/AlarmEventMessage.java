package com.aicheck.alarm.application.dto;

import com.aicheck.alarm.domain.Alarm;
import com.aicheck.alarm.domain.Type;

public record AlarmEventMessage(
	Long memberId,
	String title,
	String body,
	Type type,
	Long endPointId
) {

	public Alarm toEntity() {
		return Alarm.builder()
			.memberId(memberId())
			.title(title())
			.body(body())
			.type(type())
			.endPointId(endPointId())
			.build();
	}
}
