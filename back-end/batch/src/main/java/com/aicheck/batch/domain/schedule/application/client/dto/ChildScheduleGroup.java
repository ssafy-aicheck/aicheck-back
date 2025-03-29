package com.aicheck.batch.domain.schedule.application.client.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildScheduleGroup {
    private Long childId;
    private String childName;
    private String image;
    private String childAccountNo;
    private List<ChildScheduleItem> schedules;
}

