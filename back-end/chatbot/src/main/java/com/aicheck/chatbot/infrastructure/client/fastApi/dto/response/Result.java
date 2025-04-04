package com.aicheck.chatbot.infrastructure.client.fastApi.dto.response;

public record Result(
	Integer amount,
	String firstCategoryName,
	String secondCategoryName,
	String title,
	String description
) {
}
