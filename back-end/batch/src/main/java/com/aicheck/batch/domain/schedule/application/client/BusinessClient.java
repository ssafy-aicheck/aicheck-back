package com.aicheck.batch.domain.schedule.application.client;

import com.aicheck.batch.domain.schedule.application.client.dto.ChildAccountInfoResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "business")
public interface BusinessClient {

    @GetMapping("/accounts/children/internal/{memberId}")
    List<ChildAccountInfoResponse> getChildrenAccounts(@PathVariable("memberId") Long memberId);
}
