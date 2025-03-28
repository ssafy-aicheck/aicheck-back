package com.aicheck.bank.domain.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyAccountFeignResponse {
    private Boolean verified;

    private String accountNo;
}
