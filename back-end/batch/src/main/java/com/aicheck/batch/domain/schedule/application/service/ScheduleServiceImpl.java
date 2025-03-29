package com.aicheck.batch.domain.schedule.application.service;

import com.aicheck.batch.domain.schedule.dto.RegisterScheduledTransferRequest;
import com.aicheck.batch.domain.schedule.entity.Schedule;
import com.aicheck.batch.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public void createSchedule(RegisterScheduledTransferRequest request, String authorizationHeader) {

        System.out.println("#################");

        Schedule schedule = RegisterScheduledTransferRequest.toEntity(request);

        scheduleRepository.save(schedule);
    }

}
