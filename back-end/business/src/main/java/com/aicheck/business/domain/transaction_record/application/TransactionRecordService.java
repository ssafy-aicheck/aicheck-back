package com.aicheck.business.domain.transaction_record.application;

import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordListResponse;
import java.time.LocalDate;

public interface TransactionRecordService {
    TransactionRecordListResponse getTransactionRecords(Long memberId, LocalDate startDate, LocalDate endDate, String type);
    TransactionRecordListResponse getChildTransactionRecords(Long memberId, Long childId, LocalDate startDate, LocalDate endDate, String type);
}

