package com.aicheck.business.domain.account.infrastructure.client;

import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import com.aicheck.business.domain.auth.dto.BankMemberFeignResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bank")
public interface BankClient {
    @GetMapping("/accounts/{bankMemberId}")
    List<FindAccountFeignResponse> findMyAccounts(@PathVariable Long bankMemberId);

    @GetMapping("/member/{email}")
    BankMemberFeignResponse findBankMemberByEmail(@PathVariable String email);
}
