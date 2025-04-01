package com.aicheck.business.domain.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TransferReceiverDto {
    private String name;
    @Setter
    private String image;
    private String accountNo;
}
