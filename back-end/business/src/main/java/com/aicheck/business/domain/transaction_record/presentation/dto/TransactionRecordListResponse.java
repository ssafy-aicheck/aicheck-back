package com.aicheck.business.domain.transaction_record.presentation.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionRecordListResponse {
    private List<DailyTransactionRecords> data;
}

