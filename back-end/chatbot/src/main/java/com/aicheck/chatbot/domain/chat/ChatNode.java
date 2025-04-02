package com.aicheck.chatbot.domain.chat;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class ChatNode {

	private Role role;
	private String message;

	@Builder
	private ChatNode(Role role, String message) {
		this.role = role;
		this.message = message;
	}
}