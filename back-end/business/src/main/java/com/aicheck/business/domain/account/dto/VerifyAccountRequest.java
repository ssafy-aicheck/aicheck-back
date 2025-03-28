package com.aicheck.business.domain.account.dto;

import lombok.Getter;

@Getter
public class VerifyAccountRequest {
    private Long accountId;
    private String password;
}
