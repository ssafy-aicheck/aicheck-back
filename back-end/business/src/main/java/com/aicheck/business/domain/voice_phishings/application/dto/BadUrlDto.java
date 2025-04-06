package com.aicheck.business.domain.voice_phishings.application.dto;

import java.time.LocalDateTime;

public record BadUrlDto(
	Long id,
	String displayName,
	String url,
	Float score,
	LocalDateTime createdAt
) {
}