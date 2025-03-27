package com.aicheck.business.domain.account.dto;

import lombok.Getter;

@Getter
public class VerifyAccountResponse {
    private Boolean verified;
    private String accountNo;
}
