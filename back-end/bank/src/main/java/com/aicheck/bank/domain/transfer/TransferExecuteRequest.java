package com.aicheck.bank.domain.transfer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferExecuteRequest {
    private String fromAccountNo;
    private String toAccountNo;
    private Long amount;
}
