package com.aicheck.business.domain.voice_phishings.application.dto;

import java.time.LocalDateTime;

public record VoicePhishingDto(
	Long id,
	String displayName,
	String phoneNumber,
	Float score,
	LocalDateTime createdAt
) {
}