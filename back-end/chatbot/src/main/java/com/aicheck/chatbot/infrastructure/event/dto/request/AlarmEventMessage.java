package com.aicheck.chatbot.infrastructure.event.dto.request;

import static com.aicheck.chatbot.infrastructure.event.Type.*;

import com.aicheck.chatbot.infrastructure.event.Type;

import lombok.Builder;

@Builder
public record AlarmEventMessage(
	Long memberId,
	String title,
	String body,
	Type type,
	Long endPointId
) {

	public static AlarmEventMessage of(Long memberId, String title, String body, Long endPointId) {
		return AlarmEventMessage.builder()
			.memberId(memberId)
			.title(title)
			.body(body)
			.type(AR)
			.endPointId(endPointId)
			.build();
	}
}