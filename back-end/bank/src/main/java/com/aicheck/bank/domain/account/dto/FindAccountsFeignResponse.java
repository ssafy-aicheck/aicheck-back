package com.aicheck.bank.domain.account.dto;

import com.aicheck.bank.domain.account.entity.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindAccountsFeignResponse {

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("account_no")
    private String accountNo;

    public static FindAccountsFeignResponse from(Account account) {
        return FindAccountsFeignResponse.builder()
                .accountId(account.getId())
                .accountNo(account.getAccountNo())
                .build();
    }

}
