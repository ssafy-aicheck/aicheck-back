package com.aicheck.business.domain.allowance;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildScheduleResponse {
    private Long scheduleId;
    private Integer amount;
    private String interval;
    private String day;
    private LocalDate startDate;
}