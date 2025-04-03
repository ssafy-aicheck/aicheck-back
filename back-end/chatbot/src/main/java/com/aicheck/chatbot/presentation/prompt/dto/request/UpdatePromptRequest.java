package com.aicheck.chatbot.presentation.prompt.dto.request;

import java.util.List;

import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;

import jakarta.validation.constraints.NotNull;

public record UpdatePromptRequest(
	@NotNull(message = "childId가 없습니다.")
	Long childId,
	@NotNull(message = "categoryDifficulties가 없습니다.")
	List<CategoryDifficulty> categoryDifficulties,
	@NotNull(message = "content가 없습니다.")
	String content
) {
}
