package com.aicheck.batch.domain.schedule.application.service;

import com.aicheck.batch.domain.schedule.dto.RegisterScheduledTransferRequest;

public interface ScheduleService {
    void createSchedule(RegisterScheduledTransferRequest request, String authorizationHeader);
}
