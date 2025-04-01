package com.aicheck.alarm.presentation.dto.response;

import com.aicheck.alarm.domain.Alarm;

import lombok.Builder;

@Builder
public record AlarmResponse(
	Long alarmId,
	String body,
	boolean isRead,
	String type,
	Long endPointId,
	String createdAt
) {

	public static AlarmResponse from(Alarm alarm) {
		return AlarmResponse.builder()
			.alarmId(alarm.getId())
			.body(alarm.getBody())
			.isRead(alarm.isRead())
			.type(alarm.getType().name())
			.endPointId(alarm.getEndPointId())
			.createdAt(alarm.getCreatedAt().toString())
			.build();
	}
}
