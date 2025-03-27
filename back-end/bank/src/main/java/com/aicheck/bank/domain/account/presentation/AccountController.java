package com.aicheck.bank.domain.account.presentation;

import com.aicheck.bank.domain.account.application.AccountService;
import com.aicheck.bank.domain.account.dto.FindAccountsFeignResponse;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignRequest;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignResponse;
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
    public ResponseEntity<List<FindAccountsFeignResponse>> getMyAccounts(@PathVariable Long memberId) {
        List<FindAccountsFeignResponse> accounts = accountService.findMyAccountsByMemberId(memberId);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyAccountFeignResponse> verifyAccount(@RequestBody VerifyAccountFeignRequest request) {
        VerifyAccountFeignResponse response = accountService.verifyAccount(request);
        return ResponseEntity.ok(response);
    }

}
