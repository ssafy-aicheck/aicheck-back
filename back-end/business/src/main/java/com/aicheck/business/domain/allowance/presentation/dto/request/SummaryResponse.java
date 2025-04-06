package com.aicheck.business.domain.allowance.presentation.dto.request;

import com.aicheck.business.domain.account.dto.DescriptionRatioResponse;
import com.aicheck.business.domain.allowance.application.client.dto.ReportSummaryResponse;

import lombok.Builder;

@Builder
public record SummaryResponse(
	Integer totalCount,
	Integer memoCount,
	String firstCategoryName,
	String secondCategoryName
) {

	public static SummaryResponse from(DescriptionRatioResponse descriptionRatio,
		ReportSummaryResponse reportSummaryResponse) {
		return SummaryResponse.builder()
			.totalCount(descriptionRatio.totalCount())
			.memoCount(descriptionRatio.memoCount())
			.firstCategoryName(reportSummaryResponse.firstCategoryName())
			.secondCategoryName(reportSummaryResponse.secondCategoryName())
			.build();
	}
}
