package com.aicheck.chatbot.infrastructure.client.batch.dto.response;

import java.time.LocalDate;

import com.aicheck.chatbot.domain.chat.Interval;

public record ScheduledAllowance(
	Integer allowance,
	LocalDate startDate,
	Interval interval
) {
}
