package com.aicheck.business.domain.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
