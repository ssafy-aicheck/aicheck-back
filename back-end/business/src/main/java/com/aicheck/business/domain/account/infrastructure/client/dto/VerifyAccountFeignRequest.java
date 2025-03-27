package com.aicheck.business.domain.account.infrastructure.client.dto;

import com.aicheck.business.domain.account.dto.VerifyAccountRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyAccountFeignRequest {
    private Long bankMemberId;
    private Long accountId;
    private String password;

    public static VerifyAccountFeignRequest from(Long bankMemberId, VerifyAccountRequest verifyAccountRequest) {
        return VerifyAccountFeignRequest.builder()
                .bankMemberId(bankMemberId)
                .accountId(verifyAccountRequest.getAccountId())
                .password(verifyAccountRequest.getPassword())
                .build();
    }
}
