package com.aicheck.business.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponse {
    private String accessToken;
    private String refreshToken;
    private Boolean isParent;
    private Boolean accountConnected;
}
