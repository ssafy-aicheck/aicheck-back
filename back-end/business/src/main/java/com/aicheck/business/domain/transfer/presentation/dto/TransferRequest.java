package com.aicheck.business.domain.transfer.presentation.dto;

import lombok.Getter;

@Getter
public class TransferRequest {
    private String receiverAccountNo;
    private Long amount;
}
