package com.aicheck.chatbot.presentation.prompt.dto.response;

import java.util.List;

import com.aicheck.chatbot.application.service.prompt.dto.response.PromptInfo;
import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;

import lombok.Builder;

@Builder
public record UpdatePromptResponse(
	Long childId,
	List<CategoryDifficulty> categoryDifficulties,
	String content
) {

	public static UpdatePromptResponse from(final PromptInfo promptInfo) {
		return UpdatePromptResponse.builder()
			.childId(promptInfo.childId())
			.categoryDifficulties(promptInfo.categoryDifficulties())
			.content(promptInfo.content())
			.build();
	}
}
