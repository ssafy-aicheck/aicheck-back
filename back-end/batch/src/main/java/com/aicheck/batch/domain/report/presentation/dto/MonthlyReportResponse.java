package com.aicheck.batch.domain.report.presentation.dto;

import com.aicheck.batch.domain.report.entity.MonthlyReport;
import com.aicheck.batch.domain.report.summary.dto.CategorySummary;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonthlyReportResponse {
    private String id;
    private String name;
    private Integer year;
    private Integer month;
    private Integer totalAmount;
    private List<CategorySummary> categories;

    public static MonthlyReportResponse from(String name, MonthlyReport report) {
        return MonthlyReportResponse.builder()
                .id(report.getId())
                .name(name)
                .year(report.getYear())
                .month(report.getMonth())
                .totalAmount(report.getTotalAmount())
                .categories(report.getCategories())
                .build();
    }

}
