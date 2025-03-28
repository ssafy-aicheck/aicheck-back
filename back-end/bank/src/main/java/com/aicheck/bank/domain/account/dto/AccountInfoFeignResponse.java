package com.aicheck.bank.domain.account.dto;

import com.aicheck.bank.domain.account.entity.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountInfoFeignResponse {
    private Long accountId;
    private String accountName;
    private String accountNo;
    private Long balance;

    public static AccountInfoFeignResponse from(Account account) {
        return AccountInfoFeignResponse.builder()
                .accountId(account.getId())
                .accountName(account.getAccountName())
                .accountNo(account.getAccountNo())
                .balance(account.getBalance())
                .build();
    }

}
