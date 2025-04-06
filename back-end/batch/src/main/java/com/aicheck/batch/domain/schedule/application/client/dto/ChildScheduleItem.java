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
                .day(calculateDay(schedule))
                .startDate(schedule.getStartDate())
                .build();
    }

    private static String calculateDay(Schedule schedule) {
        return switch (schedule.getInterval()) {
            case MONTHLY -> String.valueOf(schedule.getStartDate().getDayOfMonth());
            case WEEKLY, BIWEEKLY -> schedule.getStartDate().getDayOfWeek().name();
        };
    }
}

