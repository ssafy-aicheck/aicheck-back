package com.aicheck.chatbot.application.service.redis.dto.request;

import java.util.List;

import com.aicheck.chatbot.application.service.prompt.dto.response.PromptInfo;
import com.aicheck.chatbot.domain.Gender;
import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;
import com.aicheck.chatbot.domain.chat.TransactionRecord;
import com.aicheck.chatbot.infrastructure.client.business.dto.response.TransactionInfoResponse;

import lombok.Builder;

@Builder
public record CustomSettingRequest(
	Long childId,
	Integer originalAllowance,
	String conversationStyle,
	Integer age,
	Gender gender,
	Float averageScore,
	List<CategoryDifficulty> categoryDifficulties,
	List<TransactionRecord> transactionRecords
) {

	public static CustomSettingRequest of(
		PromptInfo promptInfo, Integer originalAllowance, TransactionInfoResponse transactionInfoResponse) {

		return CustomSettingRequest.builder()
			.childId(promptInfo.childId())
			.originalAllowance(originalAllowance)
			.conversationStyle(promptInfo.content())
			.age(promptInfo.age())
			.gender(promptInfo.gender())
			.averageScore(transactionInfoResponse.averageScore())
			.categoryDifficulties(promptInfo.categoryDifficulties())
			.transactionRecords(transactionInfoResponse.transactionRecords())
			.build();
	}
}
