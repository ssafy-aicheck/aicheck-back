package com.aicheck.bank.domain.account.infrastructure.feign.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TransferReceiverResponse {
    private String name;
    private String image;
    private String accountNo;
}
