package com.aicheck.business.domain.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindAccountFeignResponse {
    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("account_no")
    private String accountNo;
}
