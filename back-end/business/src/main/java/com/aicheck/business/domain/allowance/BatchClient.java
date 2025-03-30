package com.aicheck.business.domain.allowance;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "batch")
public interface BatchClient {
    @GetMapping("/schedules/child/{childId}")
    ChildScheduleResponse getChildSchedule(@PathVariable("childId") Long childId);
}
