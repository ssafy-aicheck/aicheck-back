package com.aicheck.chatbot.application.service.redis.dto.request;

import static com.aicheck.chatbot.domain.chat.Role.*;

import com.aicheck.chatbot.domain.chat.Role;

import lombok.Builder;

@Builder
public record AIMessage(
	Role role,
	String message
) {

	public static AIMessage of(String message) {
		return AIMessage.builder()
			.role(AI)
			.message(message)
			.build();
	}
}
