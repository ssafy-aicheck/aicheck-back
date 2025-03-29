package com.aicheck.batch.domain.schedule.application.client.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildScheduleResponse {
    private Long scheduleId;
    private Long childId;
    private String childName;
    private String image;
    private String childAccountNo;
    private Integer amount;
    private String interval;
    private String day;
    private LocalDate startDate;
}

