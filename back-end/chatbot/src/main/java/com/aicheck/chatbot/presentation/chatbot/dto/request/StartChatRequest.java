package com.aicheck.chatbot.presentation.chatbot.dto.request;

import com.aicheck.chatbot.domain.chat.Type;

import jakarta.validation.constraints.NotNull;

public record StartChatRequest(
	@NotNull(message = "type이 없습니다.")
	Type type
) {
}
