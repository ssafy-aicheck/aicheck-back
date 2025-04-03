package com.aicheck.batch.domain.report.summary.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class MonthlyReportResponse {
    int year;
    int month;
    int totalAmount;
    List<CategorySummary> summaries;
}