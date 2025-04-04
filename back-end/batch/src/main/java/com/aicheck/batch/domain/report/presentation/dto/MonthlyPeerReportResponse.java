package com.aicheck.batch.domain.report.presentation.dto;

import com.aicheck.batch.domain.report.entity.MonthlyPeerReport;
import com.aicheck.batch.domain.report.summary.dto.CategorySummary;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonthlyPeerReportResponse {
    @Id
    private String id;

    private String peerGroup;
    private int year;
    private int month;
    private int totalAmount;

    private List<CategorySummary> categories;

    public static MonthlyPeerReportResponse from(MonthlyPeerReport report) {
        return MonthlyPeerReportResponse.builder()
                .id(report.getId())
                .peerGroup(report.getPeerGroup())
                .year(report.getYear())
                .month(report.getMonth())
                .totalAmount(report.getTotalAmount())
                .categories(report.getCategories())
                .build();
    }

}
