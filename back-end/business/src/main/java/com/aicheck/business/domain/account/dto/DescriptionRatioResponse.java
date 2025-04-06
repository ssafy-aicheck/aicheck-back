package com.aicheck.business.domain.account.dto;

import lombok.Builder;

@Builder
public record DescriptionRatioResponse(
	Integer totalCount,
	Integer memoCount
) {

	public static DescriptionRatioResponse of(Integer totalCount, Integer memoCount) {
		return DescriptionRatioResponse.builder().totalCount(totalCount).memoCount(memoCount).build();
	}
}
