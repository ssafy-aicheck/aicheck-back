package com.aicheck.bank.domain.account.presentation;

import com.aicheck.bank.domain.account.application.AccountService;
import com.aicheck.bank.domain.account.dto.AccountInfoFeignResponse;
import com.aicheck.bank.domain.account.dto.FindAccountsFeignResponse;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignRequest;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignResponse;
import com.aicheck.bank.domain.account.dto.VerifyAccountPasswordFeignRequest;
import com.aicheck.bank.domain.account.dto.VerifyAccountPasswordFeignResponse;
import com.aicheck.bank.domain.account.infrastructure.feign.dto.TransferSenderResponse;
import com.aicheck.bank.domain.account.infrastructure.feign.dto.TransferReceiverResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{memberId}")
    public ResponseEntity<List<FindAccountsFeignResponse>> getMyAccountsInfo(@PathVariable Long memberId) {
        List<FindAccountsFeignResponse> accounts = accountService.findMyAccountsByMemberId(memberId);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyAccountFeignResponse> verifyAccount(@RequestBody VerifyAccountFeignRequest request) {
        VerifyAccountFeignResponse response = accountService.verifyAccount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-password")
    public ResponseEntity<VerifyAccountPasswordFeignResponse> verifyAccountPassword(@RequestBody VerifyAccountPasswordFeignRequest request) {
        VerifyAccountPasswordFeignResponse response = accountService.verifyAccountPassword(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountNo}/info")
    public ResponseEntity<AccountInfoFeignResponse> findAccountInfo(@PathVariable String accountNo) {
        return ResponseEntity.ok(accountService.getAccountInfoByNumber(accountNo));
    }

    @PostMapping("/children")
    public ResponseEntity<?> findChildrenAccountInfos(@RequestBody List<String> childrenAccountNos) {
        return ResponseEntity.ok(accountService.findChildrenAccountInfos(childrenAccountNos));
    }

    @GetMapping("/receiver/{accountNo}")
    public ResponseEntity<TransferReceiverResponse> findReceiverAccountInfo(@PathVariable String accountNo) {
        return ResponseEntity.ok(accountService.findTransferReceiverInfo(accountNo));
    }

    @GetMapping("/sender/{accountNo}")
    public ResponseEntity<TransferSenderResponse> findSenderAccountInfo(@PathVariable String accountNo) {
        return ResponseEntity.ok(accountService.findSenderAccountInfo(accountNo));
    }

}
