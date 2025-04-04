package com.aicheck.business.domain.allowance.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateAllowanceIncreaseRequest {
    private Integer increaseAmount;
    private String reason;
    private String reportId;
}
