package com.aicheck.business.domain.transaction_record.application.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CalendarRecordListResponse {
    private List<CalendarRecordItem> calendar;
}
