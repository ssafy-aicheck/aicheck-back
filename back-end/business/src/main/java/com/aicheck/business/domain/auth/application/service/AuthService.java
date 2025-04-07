package com.aicheck.business.domain.auth.application.service;

import com.aicheck.business.domain.auth.dto.SignInRequest;
import com.aicheck.business.domain.auth.dto.SignInResponse;
import com.aicheck.business.domain.auth.dto.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    void signUp(SignupRequest request);

    void signUpChild(SignupRequest request, Long managerId);

    SignInResponse signIn(SignInRequest request);
}
