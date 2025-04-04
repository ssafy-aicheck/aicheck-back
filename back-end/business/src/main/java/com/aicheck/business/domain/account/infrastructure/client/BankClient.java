package com.aicheck.business.domain.account.infrastructure.client;

import com.aicheck.business.domain.account.dto.AccountInfoResponse;
import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import com.aicheck.business.domain.account.dto.VerifyAccountPasswordRequest;
import com.aicheck.business.domain.account.dto.VerifyAccountResponse;
import com.aicheck.business.domain.account.infrastructure.client.dto.VerifyAccountFeignRequest;
import com.aicheck.business.domain.auth.dto.BankMemberFeignResponse;
import com.aicheck.business.domain.transfer.dto.TransferReceiverDto;
import com.aicheck.business.domain.transfer.dto.TransferSenderDto;
import com.aicheck.business.domain.transfer.dto.feign.TransferExecuteRequest;
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

    @PostMapping("/accounts/verify-password")
    VerifyAccountResponse verifyAccountPassword(@RequestBody VerifyAccountPasswordRequest verifyAccountPasswordRequest);

    @GetMapping("/accounts/{accountNo}/info")
    AccountInfoResponse findAccountsInfo(@PathVariable String accountNo);

    @PostMapping("/accounts/verify")
    VerifyAccountResponse verifyAccount(@RequestBody VerifyAccountFeignRequest verifyAccountFeignRequest);

    @PostMapping("/accounts/children")
    List<AccountInfoResponse> findAccountsInfoList(@RequestBody List<String> childrenAccountNos);

    @GetMapping("/accounts/receiver/{accountNo}")
    TransferReceiverDto findReceiverAccountInfo(@PathVariable String accountNo);

    @GetMapping("/accounts/sender/{accountNo}")
    TransferSenderDto findSenderAccountInfo(@PathVariable String accountNo);

    @PostMapping("/transfer")
    void executeTransfer(@RequestBody TransferExecuteRequest request);

}
