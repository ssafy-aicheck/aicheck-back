package com.aicheck.chatbot.domain.categoryDifficulty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubCategoryDifficulty {
	private Integer subCategoryId;
	private String subCategoryName;
	private Difficulty difficulty;

	@Builder
	private SubCategoryDifficulty(Integer subCategoryId, String subCategoryName, Difficulty difficulty) {
		this.subCategoryId = subCategoryId;
		this.subCategoryName = subCategoryName;
		this.difficulty = difficulty;
	}
}