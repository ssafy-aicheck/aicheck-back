package com.aicheck.business.domain.transaction_record.repository;

import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordListResponse;
import com.aicheck.business.domain.transaction_record.entity.TransactionType;
import java.time.LocalDate;

public interface TransactionRecordQueryRepository {
    TransactionRecordListResponse findTransactionRecords(Long memberId, LocalDate startDate, LocalDate endDate, TransactionType type);
}

