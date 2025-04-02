package com.aicheck.chatbot.presentation.chatbot.dto.request;

import com.aicheck.chatbot.domain.chat.Type;

import jakarta.validation.constraints.NotNull;

public record SendChatRequest(
	@NotNull(message = "type이 없습니다.")
	Type type,
	@NotNull(message = "message가 없습니다.")
	String message
) {
}
