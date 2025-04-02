package com.aicheck.chatbot.presentation.prompt.dto.response;

import java.util.List;

import com.aicheck.chatbot.domain.Prompt;
import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;

import lombok.Builder;

@Builder
public record PromptResponse(
	Long childId,
	List<CategoryDifficulty> categoryDifficulties,
	String content
) {

	public static PromptResponse of(Prompt prompt, List<CategoryDifficulty> categoryDifficulties) {
		return PromptResponse.builder()
			.childId(prompt.getChildId())
			.categoryDifficulties(categoryDifficulties)
			.content(prompt.getContent())
			.build();
	}
}
