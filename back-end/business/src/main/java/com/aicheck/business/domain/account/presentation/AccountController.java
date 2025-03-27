package com.aicheck.business.domain.account.presentation;

import com.aicheck.business.domain.account.application.service.AccountService;
import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
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
    public ResponseEntity<List<FindAccountFeignResponse>> findMyAccounts(@PathVariable Long memberId) {
        return ResponseEntity.ok(accountService.findMyAccounts(memberId));
    }

}
