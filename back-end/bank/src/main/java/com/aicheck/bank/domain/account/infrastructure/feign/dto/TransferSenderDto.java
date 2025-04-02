package com.aicheck.bank.domain.account.infrastructure.feign.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TransferSenderDto {
    private String accountNo;
    private String accountName;
    private Long balance;
}
