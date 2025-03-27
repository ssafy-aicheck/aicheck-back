package com.aicheck.bank.domain.account.dto;

import com.aicheck.bank.domain.account.entity.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindAccountsFeignResponse {
    private Long accountId;
    private String bankName;
    private String accountName;
    private String accountNo;

    public static FindAccountsFeignResponse from(Account account) {
        return FindAccountsFeignResponse.builder()
                .accountId(account.getId())
                .accountNo(account.getAccountNo())
                .build();
    }

}
