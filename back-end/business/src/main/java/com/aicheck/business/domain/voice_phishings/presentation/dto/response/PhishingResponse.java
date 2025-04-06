package com.aicheck.business.domain.voice_phishings.presentation.dto.response;

import java.time.LocalDateTime;

import com.aicheck.business.domain.voice_phishings.application.dto.BadUrlDto;
import com.aicheck.business.domain.voice_phishings.application.dto.VoicePhishingDto;
import com.aicheck.business.domain.voice_phishings.entity.PhishingType;

import lombok.Builder;

@Builder
public record PhishingResponse(
	Long id,
	String displayName,
	PhishingType type,
	String url,
	String phoneNumber,
	Float score,
	LocalDateTime createdAt
) implements Comparable<PhishingResponse> {

	public static PhishingResponse from(VoicePhishingDto voicePhishingDto) {
		return PhishingResponse.builder()
	}

	public static PhishingResponse from(BadUrlDto badUrlDto) {
		return PhishingResponse.builder()
	}

	@Override
	public int compareTo(PhishingResponse o) {
		return o.createdAt().compareTo(this.createdAt());
	}
}
