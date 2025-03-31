package com.aicheck.business.domain.transaction_record.presentation.dto;

import com.aicheck.business.domain.transaction_record.application.dto.TransactionRecordItem;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DailyTransactionRecords {
    private LocalDate date;
    private List<TransactionRecordItem> records;
}
