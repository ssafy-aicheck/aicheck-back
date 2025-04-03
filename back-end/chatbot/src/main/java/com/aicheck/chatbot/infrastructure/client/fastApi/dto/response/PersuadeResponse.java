package com.aicheck.chatbot.infrastructure.client.fastApi.dto.response;

public record PersuadeResponse(
	String message,
	Boolean isPersuaded,
	Result result
) {
}
