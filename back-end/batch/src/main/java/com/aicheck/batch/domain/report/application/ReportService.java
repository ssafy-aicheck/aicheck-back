package com.aicheck.batch.domain.report.application;

import com.aicheck.batch.domain.report.application.dto.MemberInfoResponse;
import com.aicheck.batch.domain.report.entity.MonthlyPeerReport;
import com.aicheck.batch.domain.report.entity.MonthlyReport;
import com.aicheck.batch.domain.report.presentation.dto.MonthlyPeerReportResponse;
import com.aicheck.batch.domain.report.presentation.dto.MonthlyReportResponse;
import com.aicheck.batch.domain.report.repository.PeerReportRepository;
import com.aicheck.batch.domain.report.repository.ReportRepository;
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

}
