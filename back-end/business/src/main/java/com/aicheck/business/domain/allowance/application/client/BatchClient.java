package com.aicheck.business.domain.allowance.application.client;

import com.aicheck.business.domain.allowance.application.client.dto.ChildScheduleResponse;
import com.aicheck.business.domain.allowance.application.client.dto.ReportSummaryResponse;
import com.aicheck.business.domain.allowance.application.client.dto.UpdateAllowanceFeignRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "batch")
public interface BatchClient {
    @GetMapping("/schedules/child/{childId}")
    ChildScheduleResponse getChildSchedule(@PathVariable("childId") Long childId);

    @GetMapping("/reports/summary/{reportId}")
    ReportSummaryResponse getReportSummaryResponse(@PathVariable("reportId") String reportId);

    @PostMapping("/schedules/internal/{childId}")
    void updateAllowanceByChildId(@PathVariable Long childId, UpdateAllowanceFeignRequest request);
}
