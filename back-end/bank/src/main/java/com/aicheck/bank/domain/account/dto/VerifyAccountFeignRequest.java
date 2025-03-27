package com.aicheck.bank.domain.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VerifyAccountFeignRequest {
    @JsonProperty("bank_member_id")
    private Long bankMemberId;
    
    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("password")
    private String password;
}
