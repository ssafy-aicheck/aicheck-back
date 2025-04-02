package com.aicheck.chatbot.presentation.chatbot.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ChatResponse(
	Boolean isPersuaded,
	String message,
	LocalDateTime createdAt
) {
}