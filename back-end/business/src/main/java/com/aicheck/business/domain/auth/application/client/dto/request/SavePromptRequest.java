package com.aicheck.business.domain.auth.application.client.dto.request;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record SavePromptRequest(
	Long childId,
	Long managerId,
	LocalDate birth,
	String gender
) {
	public static SavePromptRequest of(Long childId, Long managerId, LocalDate birth, String gender) {
		return SavePromptRequest.builder()
			.childId(childId)
			.managerId(managerId)
			.birth(birth)
			.gender(gender)
			.build();
	}
}
