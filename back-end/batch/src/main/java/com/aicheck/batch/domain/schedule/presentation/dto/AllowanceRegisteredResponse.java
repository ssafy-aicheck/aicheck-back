package com.aicheck.batch.domain.schedule.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllowanceRegisteredResponse {
    private boolean registered;
}
