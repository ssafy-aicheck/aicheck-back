package com.aicheck.batch.domain.schedule.application.service;

import com.aicheck.batch.domain.schedule.application.client.dto.ScheduleListResponse;
import com.aicheck.batch.domain.schedule.dto.RegisterScheduledTransferRequest;

public interface ScheduleService {
    void createSchedule(Long memberId, RegisterScheduledTransferRequest request);

    ScheduleListResponse getSchedules(Long memberId);

    void updateSchedule(Long memberId, Long scheduleId, RegisterScheduledTransferRequest request);

    void deleteSchedule(Long scheduleId);
}
