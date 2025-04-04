package com.aicheck.business.domain.transaction_record.presentation.dto;

import com.aicheck.business.domain.transaction_record.entity.TransactionRecord;
import com.aicheck.business.domain.transaction_record.entity.TransactionType;

import lombok.Builder;

@Builder
public record TransactionRecordDto(
	String firstCategoryName,
	String secondCategoryName,
	TransactionType transactionType,
	Integer amount,
	Integer rating
) {

	public static TransactionRecordDto from(TransactionRecord transactionRecord) {
		return TransactionRecordDto.builder()
			.firstCategoryName(String.valueOf(transactionRecord.getFirstCategory()))
			.secondCategoryName(String.valueOf(transactionRecord.getSecondCategory()))
			.transactionType(transactionRecord.getType())
			.amount(transactionRecord.getAmount())
			.rating(transactionRecord.getRating())
			.build();
	}
}
