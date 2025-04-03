package com.aicheck.chatbot.domain.chat;

import java.util.List;

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
	List<CategoryDifficulty> categoryDifficulties,
	List<TransactionRecord> transactionRecords
) {

	public CustomSetting(Integer originalAllowance, String conversationStyle,
		Integer age, Gender gender, Float averageScore, List<CategoryDifficulty> categoryDifficulties,
		List<TransactionRecord> transactionRecords) {
		this.originalAllowance = originalAllowance;
		this.conversationStyle = conversationStyle;
		this.age = age;
		this.gender = gender;
		this.averageScore = averageScore;
		this.categoryDifficulties = categoryDifficulties;
		this.transactionRecords = transactionRecords;
	}
}