package com.aicheck.business.domain.account.infrastructure.client;

import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import com.aicheck.business.domain.account.dto.VerifyAccountResponse;
import com.aicheck.business.domain.account.infrastructure.client.dto.VerifyAccountFeignRequest;
import com.aicheck.business.domain.auth.dto.BankMemberFeignResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bank")
public interface BankClient {
    @GetMapping("/accounts/{bankMemberId}")
    List<FindAccountFeignResponse> findMyAccounts(@PathVariable Long bankMemberId);

    @GetMapping("/member/{email}")
    BankMemberFeignResponse findBankMemberByEmail(@PathVariable String email);

    @PostMapping("/accounts/verify")
    VerifyAccountResponse verifyAccount(@RequestBody VerifyAccountFeignRequest verifyAccountFeignRequest);

}
