package com.aicheck.bank.domain.account.dto;

import lombok.Getter;

@Getter
public class VerifyAccountPasswordFeignRequest {
    private Long accountId;
    private String password;
}
