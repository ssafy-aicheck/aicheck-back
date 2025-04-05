package com.aicheck.chatbot.domain.chat;

import com.aicheck.chatbot.application.service.redis.dto.request.AIMessage;
import com.aicheck.chatbot.application.service.redis.dto.request.MemberMessage;

import lombok.Builder;

@Builder
public record ChatNode(
	Role role,
	String message
){

	public static ChatNode from(MemberMessage memberMessage){
		return ChatNode.builder()
			.role(memberMessage.role())
			.message(memberMessage.message())
			.build();
	}

	public static ChatNode from(AIMessage aiMessage){
		return ChatNode.builder()
			.role(aiMessage.role())
			.message(aiMessage.message())
			.build();
	}
}