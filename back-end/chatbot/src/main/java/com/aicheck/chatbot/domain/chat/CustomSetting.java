package com.aicheck.chatbot.domain.chat;

import java.util.List;

import com.aicheck.chatbot.application.service.redis.dto.request.CustomSettingRequest;
import com.aicheck.chatbot.domain.Gender;
import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;

import lombok.Builder;

@Builder
public record CustomSetting(
	Integer originalAllowance,
	String conversationStyle,
	Integer age,
	Gender gender,
	Float averageScore,
	Interval interval,
	List<CategoryDifficulty> categoryDifficulties,
	List<TransactionRecord> transactionRecords
) {

	public static CustomSetting from(final CustomSettingRequest request) {
		return CustomSetting.builder()
			.originalAllowance(request.originalAllowance())
			.conversationStyle(request.conversationStyle())
			.age(request.age())
			.gender(request.gender())
			.averageScore(request.averageScore())
			.interval(request.interval())
			.categoryDifficulties(request.categoryDifficulties())
			.transactionRecords(request.transactionRecords())
			.build();
	}
}