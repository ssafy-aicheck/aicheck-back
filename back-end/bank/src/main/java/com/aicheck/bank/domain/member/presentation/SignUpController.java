package com.aicheck.bank.domain.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.bank.domain.member.application.SignUpService;
import com.aicheck.bank.domain.member.presentation.dto.request.SignUpRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

	private final SignUpService signUpService;

	@PostMapping
	public ResponseEntity<Void> signup(@Valid @RequestBody SignUpRequest request){
		signUpService.signup(request);
		return ResponseEntity.ok().build();
	}
}
