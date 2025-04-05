package com.aicheck.chatbot.presentation.chatbot.dto.response;

import static java.time.LocalDateTime.*;

import java.time.LocalDateTime;

import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.QuestionResponse;
import com.aicheck.chatbot.domain.chat.Judge;

import lombok.Builder;

@Builder
public record QuestionChatResponse(
	String message,
	Judge judge,
	LocalDateTime createdAt
) {

	public static QuestionChatResponse from(final QuestionResponse question) {
		return QuestionChatResponse.builder()
			.message(question.message())
			.judge(question.judge())
			.createdAt(now())
			.build();
	}
}
