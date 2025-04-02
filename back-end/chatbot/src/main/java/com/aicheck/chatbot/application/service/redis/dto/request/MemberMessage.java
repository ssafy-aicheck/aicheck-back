package com.aicheck.chatbot.application.service.redis.dto.request;

import static com.aicheck.chatbot.domain.chat.Role.*;

import com.aicheck.chatbot.domain.chat.Role;

import lombok.Builder;

@Builder
public record MemberMessage(
	 Role role,
	 String message
) {

	public static MemberMessage of(String message) {
		return MemberMessage.builder()
			.role(MEMBER)
			.message(message)
			.build();
	}
}
