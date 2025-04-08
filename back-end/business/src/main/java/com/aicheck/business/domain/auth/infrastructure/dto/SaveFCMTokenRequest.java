package com.aicheck.business.domain.auth.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveFCMTokenRequest {
    private Long memberId;
    private String token;
}
