package com.aicheck.batch.domain.schedule.application.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountInfoFeignResponse {
    private String accountNo;
}
