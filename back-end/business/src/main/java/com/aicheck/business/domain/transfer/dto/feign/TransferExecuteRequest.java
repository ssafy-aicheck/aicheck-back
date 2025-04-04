package com.aicheck.business.domain.transfer.dto.feign;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferExecuteRequest {
    private String fromAccountNo;
    private String toAccountNo;
    private Long amount;
}
