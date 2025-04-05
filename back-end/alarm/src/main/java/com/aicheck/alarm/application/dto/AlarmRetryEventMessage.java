package com.aicheck.alarm.application.dto;

import lombok.Builder;

@Builder
public record AlarmRetryEventMessage(
	String token,
	String title,
	String body,
	int retryCount
) {
	public static AlarmRetryEventMessage from(final AlarmEventMessage message, final String token) {
		return AlarmRetryEventMessage.builder()
			.token(token)
			.title(message.title())
			.body(message.body())
			.retryCount(0)
			.build();
	}

	public AlarmRetryEventMessage nextRetry() {
		return AlarmRetryEventMessage.builder()
			.token(token)
			.title(title)
			.body(body)
			.retryCount(retryCount + 1)
			.build();
	}
}