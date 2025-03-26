package com.aicheck.business.domain.auth.application.service;

import com.aicheck.business.domain.auth.dto.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    void signup(SignupRequest request);
}
