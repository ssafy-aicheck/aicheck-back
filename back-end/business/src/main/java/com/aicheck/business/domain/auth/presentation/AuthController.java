package com.aicheck.business.domain.auth.presentation;

import com.aicheck.business.domain.auth.application.service.AuthService;
import com.aicheck.business.domain.auth.dto.SignInRequest;
import com.aicheck.business.domain.auth.dto.SignInResponse;
import com.aicheck.business.domain.auth.dto.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest request) {
        authService.signUp(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest request) {
        SignInResponse signInResponse = authService.signIn(request);
        return new ResponseEntity<>(signInResponse, HttpStatus.OK);
    }

}
