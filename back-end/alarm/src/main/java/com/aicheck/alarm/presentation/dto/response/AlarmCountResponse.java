package com.aicheck.alarm.presentation.dto.response;

import lombok.Builder;

@Builder
public record AlarmCountResponse(
	Long count
) {
	public static AlarmCountResponse of(Long count) {
		return AlarmCountResponse.builder().count(count).build();
	}
}
