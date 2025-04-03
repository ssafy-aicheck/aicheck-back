package com.aicheck.chatbot.domain.chat;

public record TransactionRecord(
	String firstCategoryName,
	String secondCategoryName,
	TransactionType transactionType,
	Integer amount,
	Short rating
) {
}
