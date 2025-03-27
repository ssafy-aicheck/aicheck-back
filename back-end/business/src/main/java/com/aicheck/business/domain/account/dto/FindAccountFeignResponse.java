package com.aicheck.business.domain.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindAccountFeignResponse {
    private Long accountId;
    private String bankName;
    private String accountName;
    private String accountNo;
}
