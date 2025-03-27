package com.aicheck.bank.domain.account.presentation;

import com.aicheck.bank.domain.account.application.AccountService;
import com.aicheck.bank.domain.account.dto.FindAccountsFeignResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        for (FindAccountsFeignResponse account : accounts) {
            System.out.println("@@@@@@@@");
            System.out.println("account = " + account.getAccountId());
            System.out.println("account = " + account.getAccountNo());
            System.out.println("account = " + account.getAccountName());
        }
        return ResponseEntity.ok(accounts);
    }

}
