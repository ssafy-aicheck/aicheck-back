package com.aicheck.chatbot.presentation.chatbot.dto.request;

import jakarta.validation.constraints.NotNull;

public record EndChatRequest(
	@NotNull(message = "type이 없습니다.")
	String type
) {
}
