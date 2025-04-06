package com.aicheck.business.domain.allowance.application.client.dto;

public record ReportSummaryResponse(
	Integer year,
	Integer month,
	String firstCategoryName,
	String secondCategoryName
) {
}
