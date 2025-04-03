package com.aicheck.chatbot.domain.categoryDifficulty;

import lombok.Builder;

@Builder
public record SubCategoryDifficulty(
	Integer subCategoryId,
	String subCategoryName,
	Difficulty difficulty
) {
}