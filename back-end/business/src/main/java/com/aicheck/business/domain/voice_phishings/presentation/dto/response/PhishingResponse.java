package com.aicheck.business.domain.voice_phishings.presentation.dto.response;

import java.time.LocalDateTime;

import com.aicheck.business.domain.voice_phishings.application.dto.BadUrlDto;
import com.aicheck.business.domain.voice_phishings.application.dto.VoicePhishingDto;
import com.aicheck.business.domain.voice_phishings.entity.PhishingType;
import com.querydsl.core.annotations.QueryProjection;

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

	@QueryProjection
	public PhishingResponse(Long id, String displayName, PhishingType type, String url, String phoneNumber, Float score,
		LocalDateTime createdAt) {
		this.id = id;
		this.displayName = displayName;
		this.type = type;
		this.url = url;
		this.phoneNumber = phoneNumber;
		this.score = score;
		this.createdAt = createdAt;
	}

	public static PhishingResponse from(VoicePhishingDto dto) {
		return PhishingResponse.builder()
			.id(dto.id())
			.displayName(dto.displayName())
			.type(PhishingType.VOICE)
			.phoneNumber(dto.phoneNumber())
			.score(dto.score())
			.createdAt(dto.createdAt())
			.build();
	}

	public static PhishingResponse from(BadUrlDto dto) {
		return PhishingResponse.builder()
			.id(dto.id())
			.displayName(dto.displayName())
			.type(PhishingType.URL)
			.url(dto.url())
			.score(dto.score())
			.createdAt(dto.createdAt())
			.build();
	}

	@Override
	public int compareTo(PhishingResponse o) {
		return o.createdAt().compareTo(this.createdAt());
	}
}
