package com.aicheck.business.domain.auth.application.client;

import com.aicheck.business.domain.auth.dto.BankMemberFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bank")
public interface BankMemberClient {

    @GetMapping("/member/{email}")
    BankMemberFeignResponse findBankMemberByEmail(@PathVariable String email);

}
