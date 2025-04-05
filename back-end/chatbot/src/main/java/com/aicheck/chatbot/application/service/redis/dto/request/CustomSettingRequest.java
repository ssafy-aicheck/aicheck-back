package com.aicheck.chatbot.application.service.redis.dto.request;

import java.util.List;

import com.aicheck.chatbot.application.service.prompt.dto.response.PromptInfo;
import com.aicheck.chatbot.domain.Gender;
import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;
import com.aicheck.chatbot.domain.chat.Interval;
import com.aicheck.chatbot.domain.chat.TransactionRecord;
import com.aicheck.chatbot.infrastructure.client.batch.dto.response.ScheduledAllowance;
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
	Interval interval,
	List<CategoryDifficulty> categoryDifficulties,
	List<TransactionRecord> transactionRecords
) {

	public static CustomSettingRequest from(
		final PromptInfo promptInfo, final ScheduledAllowance scheduledAllowance,
		final TransactionInfoResponse transactionInfoResponse) {

		return CustomSettingRequest.builder()
			.childId(promptInfo.childId())
			.originalAllowance(scheduledAllowance.allowance())
			.conversationStyle(promptInfo.content())
			.age(promptInfo.age())
			.gender(promptInfo.gender())
			.averageScore(transactionInfoResponse.averageScore())
			.interval(scheduledAllowance.interval())
			.categoryDifficulties(promptInfo.categoryDifficulties())
			.transactionRecords(transactionInfoResponse.transactionRecords())
			.build();
	}
}
