package com.aicheck.business.global.infrastructure.event.dto.request;

import com.aicheck.business.global.infrastructure.event.Type;

import lombok.Builder;

@Builder
public record AlarmEventMessage(
	Long memberId,
	String title,
	String body,
	Type type,
	Long endPointId
) {

	public static AlarmEventMessage of(Long memberId, String title, String body, Type type, Long endPointId) {
		return AlarmEventMessage.builder()
			.memberId(memberId)
			.title(title)
			.body(body)
			.type(type)
			.endPointId(endPointId)
			.build();
	}
}