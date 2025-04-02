package com.aicheck.chatbot.application.service.ai.dto.response;

public record AIChatResponse(
	String message,
	Boolean isPersuaded
) {
}
