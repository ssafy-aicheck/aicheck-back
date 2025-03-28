package com.aicheck.business.domain.account.infrastructure.client.dto;

import com.aicheck.business.domain.account.dto.RegisterMainAccountRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyAccountFeignRequest {
    private Long bankMemberId;
    private Long accountId;

    public static VerifyAccountFeignRequest from(Long bankMemberId, RegisterMainAccountRequest registerMainAccountRequest) {
        return VerifyAccountFeignRequest.builder()
                .bankMemberId(bankMemberId)
                .accountId(registerMainAccountRequest.getAccountId())
                .build();
    }
}
