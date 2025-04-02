package com.aicheck.business.domain.transaction_record.application.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CalendarRecordItem {
    private LocalDate date;
    private Long sum;
}