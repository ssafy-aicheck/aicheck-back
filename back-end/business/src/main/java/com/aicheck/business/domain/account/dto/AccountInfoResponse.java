package com.aicheck.business.domain.account.dto;

import lombok.Getter;

@Getter
public class AccountInfoResponse {
    private String accountId;
    private String accountName;
    private String accountNo;
    private Long balance;
}
