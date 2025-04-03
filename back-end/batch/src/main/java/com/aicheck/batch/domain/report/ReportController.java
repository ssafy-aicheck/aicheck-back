package com.aicheck.batch.domain.report;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportScheduler reportScheduler;

    @GetMapping("/test")
    public String test() {
        List<?> records = reportScheduler.collectMonthlyTransactionStatistics();

        System.out.println(records);
        return "@@@";
    }

}
