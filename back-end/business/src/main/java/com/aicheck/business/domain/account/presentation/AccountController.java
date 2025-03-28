package com.aicheck.business.domain.account.presentation;

import com.aicheck.business.domain.account.application.service.AccountService;
import com.aicheck.business.domain.account.dto.AccountInfoResponse;
import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import com.aicheck.business.domain.account.dto.VerifyAccountPasswordRequest;
import com.aicheck.business.domain.account.dto.RegisterMainAccountRequest;
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
    public ResponseEntity<VerifyAccountResponse> registerMainAccount(@CurrentMemberId Long memberId,
                                                                 @RequestBody RegisterMainAccountRequest request) {
        accountService.registerMainAccount(memberId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check")
    public ResponseEntity<Void> verifyAccountPassword(@CurrentMemberId Long memberId,
                                                      @RequestBody VerifyAccountPasswordRequest request) {
        accountService.verifyAccountPassword(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<AccountInfoResponse> findMyAccountsInfo(@CurrentMemberId Long memberId) {
        AccountInfoResponse account = accountService.findMyMainAccountInfo(memberId);
        return ResponseEntity.ok(account);
    }

}
