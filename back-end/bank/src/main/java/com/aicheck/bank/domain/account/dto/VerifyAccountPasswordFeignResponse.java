package com.aicheck.bank.domain.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyAccountPasswordFeignResponse {
    private Boolean verified;
    private String accountNo;
}
