package com.aicheck.bank.domain.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyAccountFeignResponse {
    @JsonProperty("verified")
    private Boolean verified;

    @JsonProperty("account_no")
    private String accountNo;
}
