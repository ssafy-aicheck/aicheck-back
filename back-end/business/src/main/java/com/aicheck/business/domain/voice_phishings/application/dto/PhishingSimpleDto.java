package com.aicheck.business.domain.voice_phishings.application.dto;

import java.time.LocalDateTime;

import com.aicheck.business.domain.voice_phishings.entity.PhishingType;
import com.querydsl.core.annotations.QueryProjection;

public record PhishingSimpleDto(
	Long memberId,
	Long managerId,
	PhishingType type,
	LocalDateTime createdAt
) {
	@QueryProjection
	public PhishingSimpleDto(Long memberId, Long managerId, PhishingType type, LocalDateTime createdAt) {
		this.memberId = memberId;
		this.managerId = managerId;
		this.type = type;
		this.createdAt = createdAt;
	}
}