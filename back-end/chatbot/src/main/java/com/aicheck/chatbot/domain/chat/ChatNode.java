package com.aicheck.chatbot.domain.chat;

import lombok.Builder;

@Builder
public record ChatNode(
	Role role,
	String message
){
	public ChatNode(Role role, String message) {
		this.role = role;
		this.message = message;
	}
}