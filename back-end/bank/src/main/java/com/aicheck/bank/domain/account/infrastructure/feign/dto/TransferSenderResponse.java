package com.aicheck.bank.domain.account.infrastructure.feign.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferSenderResponse {
    private String accountNo;
    private String accountName;
    private Long balance;
}
