package com.aicheck.chatbot.presentation.chatbot.dto.request;

import jakarta.validation.constraints.NotNull;

public record SendChatRequest(
	@NotNull(message = "type이 없습니다.")
	String type,
	@NotNull(message = "message가 없습니다.")
	String message
) {
}
