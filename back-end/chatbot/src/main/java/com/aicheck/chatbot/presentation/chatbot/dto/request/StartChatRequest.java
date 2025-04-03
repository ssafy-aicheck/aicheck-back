package com.aicheck.chatbot.presentation.chatbot.dto.request;

import com.aicheck.chatbot.domain.chat.ChatType;

import jakarta.validation.constraints.NotNull;

public record StartChatRequest(
	@NotNull(message = "type이 없습니다.")
	ChatType chatType
) {
}
