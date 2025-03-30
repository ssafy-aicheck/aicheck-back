package com.aicheck.batch.domain.schedule.application.client.dto;

import com.aicheck.batch.domain.schedule.entity.Schedule;
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

    public static ChildScheduleItem from(Schedule schedule) {
        return ChildScheduleItem.builder()
                .scheduleId(schedule.getId())
                .amount(schedule.getAmount())
                .interval(schedule.getInterval().name())
                .startDate(schedule.getStartDate())
                .build();
    }

}

