package com.aicheck.business.domain.account.presentation;

import com.aicheck.business.domain.account.application.service.AccountService;
import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import com.aicheck.business.domain.account.dto.VerifyAccountRequest;
import com.aicheck.business.domain.account.dto.VerifyAccountResponse;
import com.aicheck.business.global.auth.annotation.CurrentMemberId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<FindAccountFeignResponse>> findMyAccounts(@CurrentMemberId Long memberId) {
        return ResponseEntity.ok(accountService.findMyAccounts(memberId));
    }

    @PostMapping
    public ResponseEntity<VerifyAccountResponse> registerAccount(@CurrentMemberId Long memberId,
                                                                 @RequestBody VerifyAccountRequest request) {
        accountService.registerAccount(memberId, request);
        return ResponseEntity.ok().build();
    }

}
