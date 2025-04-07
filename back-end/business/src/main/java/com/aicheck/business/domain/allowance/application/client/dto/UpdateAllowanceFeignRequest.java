package com.aicheck.business.domain.allowance.application.client.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAllowanceFeignRequest {
    private Integer amount;
}
