package com.aicheck.business.domain.auth.application.client.dto.request;

import java.time.LocalDate;

import com.aicheck.business.domain.auth.domain.entity.Gender;

import lombok.Builder;

@Builder
public record SavePromptRequest(
	Long childId,
	Long managerId,
	LocalDate birth,
	Gender gender
) {
	public static SavePromptRequest of(Long childId, Long managerId, LocalDate birth, Gender gender) {
		return SavePromptRequest.builder()
			.childId(childId)
			.managerId(managerId)
			.birth(birth)
			.gender(gender)
			.build();
	}
}
