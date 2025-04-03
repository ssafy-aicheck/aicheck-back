package com.aicheck.chatbot.infrastructure.client.fastApi.dto.response;

import com.aicheck.chatbot.domain.chat.Judge;

public record QuestionResponse(
	String message,
	Judge judge
) {
}
