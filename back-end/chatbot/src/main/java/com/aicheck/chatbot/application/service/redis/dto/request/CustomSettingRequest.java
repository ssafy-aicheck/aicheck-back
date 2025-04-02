package com.aicheck.chatbot.application.service.redis.dto.request;

import java.util.List;

import com.aicheck.chatbot.application.service.prompt.dto.response.PromptInfo;
import com.aicheck.chatbot.domain.Gender;
import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;
import com.aicheck.chatbot.infrastructure.client.business.dto.response.MemberInfo;

import lombok.Builder;

@Builder
public record CustomSettingRequest(
	Long childId,
	Integer originalAllowance,
	String conversationStyle,
	Integer age,
	Gender gender,
	Float averageScore,
	List<CategoryDifficulty> categoryDifficulties
) {

	public static CustomSettingRequest of(Long childId, PromptInfo promptInfo, MemberInfo memberInfo) {
		return CustomSettingRequest.builder()
			.childId(childId)
			.originalAllowance(memberInfo.originalAllowance())
			.conversationStyle(promptInfo.content())
			.age(promptInfo.age())
			.gender(promptInfo.gender())
			.averageScore(memberInfo.averageScore())
			.categoryDifficulties(promptInfo.categoryDifficulties())
			.build();
	}
}
