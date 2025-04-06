package com.aicheck.batch.domain.report.application;

import com.aicheck.batch.domain.report.application.dto.MemberInfoResponse;
import com.aicheck.batch.domain.report.entity.MonthlyPeerReport;
import com.aicheck.batch.domain.report.entity.MonthlyReport;
import com.aicheck.batch.domain.report.presentation.dto.MonthlyPeerReportResponse;
import com.aicheck.batch.domain.report.presentation.dto.MonthlyReportResponse;
import com.aicheck.batch.domain.report.presentation.dto.ReportSummaryResponse;
import com.aicheck.batch.domain.report.repository.PeerReportRepository;
import com.aicheck.batch.domain.report.repository.ReportRepository;
import com.aicheck.batch.domain.report.summary.dto.CategorySummary;
import com.aicheck.batch.domain.report.summary.dto.SubCategorySummary;
import com.aicheck.batch.domain.report.util.PeerGroupUtils;
import com.aicheck.batch.domain.schedule.application.client.BusinessClient;
import com.aicheck.batch.global.error.BatchException;
import com.aicheck.batch.global.error.ScheduleErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final BusinessClient businessClient;
    private final ReportRepository reportRepository;
    private final PeerReportRepository peerReportRepository;

    public MonthlyReportResponse findMonthlyReport(Long childId, int year, int month) {
        MemberInfoResponse memberInfo = businessClient.getMemberInfo(childId);
        MonthlyReport monthlyReport = reportRepository.findByYearAndMonthAndChildId(year, month, childId)
                .orElseThrow(() -> new BatchException(ScheduleErrorCodes.REPORT_NOT_FOUND));
        return MonthlyReportResponse.from(memberInfo.getName(), monthlyReport);
    }

    public MonthlyPeerReportResponse findMonthlyPeerReport(Long childId, int year, int month) {
        MemberInfoResponse memberInfo = businessClient.getMemberInfo(childId);
        String peerGroup = PeerGroupUtils.getPeerGroup(memberInfo.getBirth(), year, month);
        System.out.println("memberInfo.getBirth() = " + memberInfo.getBirth());
        System.out.println("peerGroup = " + peerGroup);
        MonthlyPeerReport peerReport = peerReportRepository.findByPeerGroupAndYearAndMonth(peerGroup, year, month)
                .orElseThrow(() -> new BatchException(ScheduleErrorCodes.REPORT_NOT_FOUND));
        return MonthlyPeerReportResponse.from(peerReport);
    }

    public ReportSummaryResponse getReportSummaryResponse(String reportId) {
        MonthlyReport report = reportRepository.findById(reportId)
            .orElseThrow(() -> new BatchException(ScheduleErrorCodes.REPORT_NOT_FOUND));

        SubCategoryScore max1 = null;
        SubCategoryScore max2 = null;

        for (CategorySummary category : report.getCategories()) {
            double categoryPercentage = category.getPercentage();

            for (SubCategorySummary sub : category.getSubCategories()) {
                double score = categoryPercentage * sub.getPercentage();
                SubCategoryScore current = new SubCategoryScore(sub.getDisplayName(), score);

                if (max1 == null || score > max1.score) {
                    max2 = max1;
                    max1 = current;
                } else if (max2 == null || score > max2.score) {
                    max2 = current;
                }
            }
        }

        if (max1 == null || max2 == null) {
            throw new BatchException(ScheduleErrorCodes.REPORT_NOT_FOUND);
        }

        return ReportSummaryResponse.of(report.getYear(), report.getMonth(), max1.name, max2.name);
    }

    private record SubCategoryScore(String name, double score) {}
}
