package com.aicheck.bank.domain.account.dto;

import lombok.Getter;

@Getter
public class VerifyAccountFeignRequest {
    private Long bankMemberId;
    private Long accountId;
}
