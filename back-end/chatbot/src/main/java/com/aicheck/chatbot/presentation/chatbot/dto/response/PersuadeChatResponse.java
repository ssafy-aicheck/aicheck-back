package com.aicheck.chatbot.presentation.chatbot.dto.response;

import java.time.LocalDateTime;

import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.PersuadeResponse;

import lombok.Builder;

@Builder
public record PersuadeChatResponse(
	String message,
	Boolean isPersuaded,
	LocalDateTime createdAt
) {

	public static PersuadeChatResponse from(final PersuadeResponse response) {
		return PersuadeChatResponse.builder()
			.message(response.message())
			.isPersuaded(response.isPersuaded())
			.createdAt(LocalDateTime.now())
			.build();
	}
}
