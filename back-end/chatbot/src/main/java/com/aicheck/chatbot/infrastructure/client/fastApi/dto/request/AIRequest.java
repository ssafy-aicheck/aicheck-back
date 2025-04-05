package com.aicheck.chatbot.infrastructure.client.fastApi.dto.request;

import java.util.List;

import com.aicheck.chatbot.domain.Gender;
import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;
import com.aicheck.chatbot.domain.chat.ChatNode;
import com.aicheck.chatbot.domain.chat.CustomSetting;
import com.aicheck.chatbot.domain.chat.Interval;
import com.aicheck.chatbot.domain.chat.TransactionRecord;

import lombok.Builder;

@Builder
public record AIRequest(
	Integer originalAllowance,
	String conversationStyle,
	Integer age,
	Gender gender,
	Float averageScore,
	Interval interval,
	List<CategoryDifficulty> categoryDifficulties,
	List<TransactionRecord> transactionRecords,
	List<ChatNode> chatHistories,
	String message
) {

	public static AIRequest from(final CustomSetting customSetting, final List<ChatNode> chatHistories,
		final String message) {
		return AIRequest.builder()
			.originalAllowance(customSetting.originalAllowance())
			.conversationStyle(customSetting.conversationStyle())
			.age(customSetting.age())
			.gender(customSetting.gender())
			.averageScore(customSetting.averageScore())
			.interval(customSetting.interval())
			.categoryDifficulties(customSetting.categoryDifficulties())
			.transactionRecords(customSetting.transactionRecords())
			.chatHistories(chatHistories)
			.message(message)
			.build();
	}
}
