package com.aicheck.batch.domain.schedule.presentation.dto;

import java.time.LocalDate;

import com.aicheck.batch.domain.schedule.dto.ScheduleDto;
import com.aicheck.batch.domain.schedule.entity.Schedule;

import lombok.Builder;

@Builder
public record ScheduledAllowanceResponse(
	Integer allowance,
	LocalDate startDate,
	Schedule.Interval interval
) {

	public static ScheduledAllowanceResponse from(ScheduleDto dto) {
		return ScheduledAllowanceResponse.builder()
			.allowance(dto.amount())
			.startDate(dto.startDate())
			.interval(dto.interval())
			.build();
	}
}
