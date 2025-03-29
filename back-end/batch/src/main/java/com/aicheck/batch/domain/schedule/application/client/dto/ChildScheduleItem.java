package com.aicheck.batch.domain.schedule.application.client.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildScheduleItem {
    private Long scheduleId;
    private Integer amount;
    private String interval;
    private String day;
    private LocalDate startDate;
}

