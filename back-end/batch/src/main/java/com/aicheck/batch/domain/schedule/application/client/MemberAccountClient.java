package com.aicheck.batch.domain.schedule.application.client;

import com.aicheck.batch.domain.schedule.application.client.dto.AccountInfoFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "business")
public interface MemberAccountClient {

    @GetMapping("/accounts/my")
    AccountInfoFeignResponse getMyMainAccount(@RequestHeader("Authorization") String bearerToken);
}

