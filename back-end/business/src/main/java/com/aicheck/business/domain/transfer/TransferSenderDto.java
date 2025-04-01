package com.aicheck.business.domain.transfer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferSenderDto {
    private String accountNo;
    private String accountName;
    private Long balance;
}
