package com.aicheck.business.domain.transaction_record.repository;

import com.aicheck.business.domain.transaction_record.entity.TransactionType;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordListResponse;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRecordQueryRepository {
    TransactionRecordListResponse findTransactionRecords(Long memberId, LocalDate startDate, LocalDate endDate,
                                                         List<TransactionType> types);
}

