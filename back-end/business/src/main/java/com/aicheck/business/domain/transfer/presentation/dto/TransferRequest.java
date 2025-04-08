package com.aicheck.business.domain.transfer.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferRequest {
    private String receiverAccountNo;
    private Long amount;
}
