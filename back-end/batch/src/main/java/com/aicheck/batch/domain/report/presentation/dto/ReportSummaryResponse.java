package com.aicheck.batch.domain.report.presentation.dto;

import lombok.Builder;

@Builder
public record ReportSummaryResponse(
	Integer year,
	Integer month,
	String firstCategoryName,
	String secondCategoryName
) {

	public static ReportSummaryResponse of(Integer year, Integer month, String firstCategoryName,
		String secondCategoryName) {
		return ReportSummaryResponse.builder()
			.year(year)
			.month(month)
			.firstCategoryName(firstCategoryName)
			.secondCategoryName(secondCategoryName)
			.build();
	}
}
