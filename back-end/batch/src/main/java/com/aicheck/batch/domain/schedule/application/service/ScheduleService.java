package com.aicheck.batch.domain.schedule.application.service;

import com.aicheck.batch.domain.schedule.application.client.dto.ChildScheduleItem;
import com.aicheck.batch.domain.schedule.application.client.dto.ScheduleListResponse;
import com.aicheck.batch.domain.schedule.dto.RegisterScheduledTransferRequest;
import com.aicheck.batch.domain.schedule.presentation.dto.AllowanceRegisteredResponse;

public interface ScheduleService {
    void createSchedule(Long memberId, RegisterScheduledTransferRequest request);

    ScheduleListResponse getSchedules(Long memberId);

    void updateSchedule(Long memberId, Long scheduleId, RegisterScheduledTransferRequest request);

    void deleteSchedule(Long scheduleId);

    ChildScheduleItem findByChildId(Long childId);

    AllowanceRegisteredResponse checkAllowanceRegistered(Long childId, String reportId);
}
