package com.aicheck.batch.domain.report.application;

import com.aicheck.batch.domain.report.entity.MonthlyReport;
import com.aicheck.batch.domain.report.repository.ReportRepository;
import com.aicheck.batch.global.error.BatchException;
import com.aicheck.batch.global.error.ScheduleErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public MonthlyReport getMonthlyReport(Long childId, int year, int month) {
        return reportRepository.findByYearAndMonthAndChildId(year, month, childId)
                .orElseThrow(() -> new BatchException(ScheduleErrorCodes.REPORT_NOT_FOUND));
    }


}
