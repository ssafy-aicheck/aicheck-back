package com.aicheck.chatbot.application.service.prompt.dto.response;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.aicheck.chatbot.domain.Gender;
import com.aicheck.chatbot.domain.Prompt;
import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;

import lombok.Builder;

@Builder
public record PromptInfo(
	Long childId,
	Long managerId,
	Integer age,
	String content,
	Gender gender,
	List<CategoryDifficulty> categoryDifficulties
) {

	public static PromptInfo of(Prompt prompt, List<CategoryDifficulty> categoryDifficulties){
		return PromptInfo.builder()
			.childId(prompt.getChildId())
			.managerId(prompt.getManagerId())
			.age(Period.between(prompt.getBirth(), LocalDate.now()).getYears())
			.content(prompt.getContent())
			.gender(prompt.getGender())
			.categoryDifficulties(categoryDifficulties)
			.build();
	}
}
