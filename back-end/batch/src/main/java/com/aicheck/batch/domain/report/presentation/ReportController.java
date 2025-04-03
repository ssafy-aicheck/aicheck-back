package com.aicheck.batch.domain.report.presentation;

import com.aicheck.batch.domain.report.application.ReportScheduler;
import com.aicheck.batch.domain.report.application.ReportService;
import com.aicheck.batch.domain.report.entity.MonthlyReport;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportScheduler reportScheduler;
    private final ReportService reportService;

    // 리포트 생성용 임시 컨트롤러
    @GetMapping("/test")
    public String test() {
        List<?> records = reportScheduler.collectMonthlyTransactionStatistics();
        return "@@@";
    }

    // 또래 리포트 생성용 임시 컨트롤러
    @GetMapping("/test2")
    public String test2() {

        return "@@@@";
    }

    @GetMapping
    public ResponseEntity<?> getReport(@RequestParam Integer year,
                                       @RequestParam Integer month,
                                       @RequestParam Long childId) {
        MonthlyReport monthlyReport = reportService.getMonthlyReport(childId, year, month);
        return ResponseEntity.ok(monthlyReport);
    }

}
