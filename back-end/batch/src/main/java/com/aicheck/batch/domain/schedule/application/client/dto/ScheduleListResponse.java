package com.aicheck.batch.domain.schedule.application.client.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleListResponse {
    private List<ChildScheduleGroup> children;
}