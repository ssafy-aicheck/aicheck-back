package com.aicheck.business.domain.voice_phishings.presentation.dto.response;

import lombok.Builder;

@Builder
public record PhishingCountsResponse(
	Float totalCountAverage,
	Integer urlCount,
	Integer voiceCount,
	Integer familyCount
) {

	public static PhishingCountsResponse of(Float totalCountAverage, Integer urlCount, Integer voiceCount,
		Integer familyCount) {
		return PhishingCountsResponse.builder()
			.totalCountAverage(totalCountAverage)
			.urlCount(urlCount)
			.voiceCount(voiceCount)
			.familyCount(familyCount)
			.build();
	}
}
