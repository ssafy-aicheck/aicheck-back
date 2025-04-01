package com.aicheck.business.domain.account.dto;

import lombok.Getter;

@Getter
public class VerifyAccountPasswordRequest {
    private Long accountId;
    private String password;
}
