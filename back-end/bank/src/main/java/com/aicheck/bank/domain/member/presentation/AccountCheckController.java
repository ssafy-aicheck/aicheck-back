package com.aicheck.bank.domain.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.bank.domain.member.application.AccountCheckService;
import com.aicheck.bank.domain.member.presentation.dto.request.AccountCheckRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/check")
public class AccountCheckController {

	private final AccountCheckService accountCheckService;

	@PostMapping
	public ResponseEntity<Void> accountCheck(@Valid @RequestBody AccountCheckRequest request) {
		accountCheckService.accountCheck(request.accountNo());
		return ResponseEntity.ok().build();
	}
}
