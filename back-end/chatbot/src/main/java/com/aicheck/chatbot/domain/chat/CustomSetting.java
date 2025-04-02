package com.aicheck.chatbot.domain.chat;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;

import com.aicheck.chatbot.domain.Gender;
import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CustomSetting {

	private Integer originalAllowance;
	private String conversationStyle;
	private Integer age;
	private Gender gender;
	private Float averageScore;
	private List<CategoryDifficulty> categoryDifficulties;

	@Builder
	private CustomSetting(Integer originalAllowance, String conversationStyle,
		Integer age, Gender gender, Float averageScore, List<CategoryDifficulty> categoryDifficulties) {
		this.originalAllowance = originalAllowance;
		this.conversationStyle = conversationStyle;
		this.age = age;
		this.gender = gender;
		this.averageScore = averageScore;
		this.categoryDifficulties = categoryDifficulties;
	}
}