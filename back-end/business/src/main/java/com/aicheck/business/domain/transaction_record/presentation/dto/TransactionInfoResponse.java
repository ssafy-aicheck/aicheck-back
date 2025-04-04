package com.aicheck.business.domain.transaction_record.presentation.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record TransactionInfoResponse(
	Float averageScore,
	List<TransactionRecordDto> transactionRecords
) {

	public static TransactionInfoResponse of(Float averageScore, List<TransactionRecordDto> transactionRecords) {
		return TransactionInfoResponse.builder()
			.averageScore(averageScore)
			.transactionRecords(transactionRecords)
			.build();
	}
}
