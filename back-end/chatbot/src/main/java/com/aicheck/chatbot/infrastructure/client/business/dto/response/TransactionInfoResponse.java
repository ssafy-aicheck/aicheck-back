package com.aicheck.chatbot.infrastructure.client.business.dto.response;

import java.util.List;

import com.aicheck.chatbot.domain.chat.TransactionRecord;

public record TransactionInfoResponse(
	Float averageScore,
	List<TransactionRecord> transactionRecords
) {
}
