package com.aicheck.business.domain.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildAccountInfoResponse {
    private Long memberId;
    private String image;
    private String name;
    private Long balance;
    private String accountNo;
    private String accountName;
}
