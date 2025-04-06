package com.aicheck.batch.domain.schedule.dto;

import com.aicheck.batch.domain.schedule.entity.Schedule;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ScheduleDto(
        Long id,
        Long memberId,
        Long childId,
        String parentAccountNo,
        String childAccountNo,
        Integer amount,
        LocalDate startDate,
        Schedule.Interval interval
) {

    public static ScheduleDto from(Schedule schedule) {
        return ScheduleDto.builder()
                .id(schedule.getId())
                .memberId(schedule.getMemberId())
                .childId(schedule.getChildId())
                .parentAccountNo(schedule.getParentAccountNo())
                .childAccountNo(schedule.getChildAccountNo())
                .amount(schedule.getAmount())
                .startDate(schedule.getStartDate())
                .interval(schedule.getInterval())
                .build();
    }
}
