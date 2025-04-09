package com.aicheck.business.domain.transaction_record.application.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CalendarRecordListResponse {
    private Long expense;
    private Long income;
    private Long sum;
    private List<CalendarRecordItem> calendar;
}
