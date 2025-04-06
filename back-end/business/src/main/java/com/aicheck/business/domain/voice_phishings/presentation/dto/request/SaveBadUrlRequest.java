package com.aicheck.business.domain.voice_phishings.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record SaveBadUrlRequest (
	@NotNull(message = "url이 없습니다.")
	String url,
	@NotNull(message = "score가 없습니다.")
	Float score
) {
}
