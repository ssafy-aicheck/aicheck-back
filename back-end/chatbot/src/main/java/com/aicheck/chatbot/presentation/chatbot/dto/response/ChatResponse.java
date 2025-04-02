package com.aicheck.chatbot.presentation.chatbot.dto.response;

import static java.time.LocalDateTime.*;

import java.time.LocalDateTime;

import com.aicheck.chatbot.application.service.ai.dto.response.AIChatResponse;

import lombok.Builder;

@Builder
public record ChatResponse(
	String message,
	Boolean isPersuaded,
	LocalDateTime createdAt
) {

	public static ChatResponse from(AIChatResponse aiChatResponse) {
		return ChatResponse.builder()
			.message(aiChatResponse.message())
			.isPersuaded(aiChatResponse.isPersuaded())
			.createdAt(now())
			.build();
	}
}