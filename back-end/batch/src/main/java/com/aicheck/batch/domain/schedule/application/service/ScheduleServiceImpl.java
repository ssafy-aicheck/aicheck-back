package com.aicheck.batch.domain.schedule.application.service;

import com.aicheck.batch.domain.report.repository.ReportRepository;
import com.aicheck.batch.domain.schedule.application.client.BusinessClient;
import com.aicheck.batch.domain.schedule.application.client.dto.ChildAccountInfoResponse;
import com.aicheck.batch.domain.schedule.application.client.dto.ChildScheduleGroup;
import com.aicheck.batch.domain.schedule.application.client.dto.ChildScheduleItem;
import com.aicheck.batch.domain.schedule.application.client.dto.ScheduleListResponse;
import com.aicheck.batch.domain.schedule.dto.RegisterScheduledTransferRequest;
import com.aicheck.batch.domain.schedule.dto.ScheduleDto;
import com.aicheck.batch.domain.schedule.entity.Schedule;
import com.aicheck.batch.domain.schedule.presentation.dto.AllowanceRegisteredResponse;
import com.aicheck.batch.domain.schedule.repository.ScheduleRepository;
import com.aicheck.batch.global.error.BatchException;
import com.aicheck.batch.global.error.ScheduleErrorCodes;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final BusinessClient businessClient;
    private final ReportRepository reportRepository;

    @Override
    @Transactional
    public void createSchedule(Long memberId, RegisterScheduledTransferRequest request) {
        scheduleRepository.deleteAllByChildId(request.getChildId());
        String parentAccount = businessClient.getAccountNumber(memberId).getAccountNo();
        String childAccount = businessClient.getAccountNumber(request.getChildId()).getAccountNo();
        Schedule schedule = RegisterScheduledTransferRequest.toEntity(
                memberId, request.getChildId(), parentAccount, childAccount, request);
        scheduleRepository.save(schedule);
    }

    @Override
    public ScheduleListResponse getSchedules(Long memberId) {
        List<ChildAccountInfoResponse> accounts = businessClient.getChildrenAccounts(memberId);
        List<Schedule> schedules = scheduleRepository.findByMemberIdAndDeletedAtIsNull(memberId);

        Map<String, List<Schedule>> scheduleMap = schedules.stream()
                .collect(Collectors.groupingBy(Schedule::getChildAccountNo));

        List<ChildScheduleGroup> children = accounts.stream()
                .map(account -> {
                    List<Schedule> childSchedules = scheduleMap.getOrDefault(account.getAccountNo(), List.of());
                    List<ChildScheduleItem> scheduleItems = childSchedules.stream()
                            .map(ChildScheduleItem::from).toList();
                    return ChildScheduleGroup.from(account, scheduleItems);
                }).toList();

        return ScheduleListResponse.builder()
                .children(children)
                .build();
    }

    @Override
    @Transactional
    public void updateSchedule(Long memberId, Long scheduleId, RegisterScheduledTransferRequest request) {
        scheduleRepository.deleteById(scheduleId);
        String parentAccount = businessClient.getAccountNumber(memberId).getAccountNo();
        String childAccount = businessClient.getAccountNumber(request.getChildId()).getAccountNo();
        Schedule schedule = RegisterScheduledTransferRequest.toEntity(
                memberId, request.getChildId(), parentAccount, childAccount, request);
        scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    @Override
    public ChildScheduleItem findByChildId(Long childId) {
        Schedule schedule = scheduleRepository.findByChildIdAndDeletedAtIsNull(childId)
                .orElseThrow(() -> new BatchException(ScheduleErrorCodes.SCHEDULE_NOT_FOUND));
        return ChildScheduleItem.from(schedule);
    }

    @Override
    public AllowanceRegisteredResponse checkAllowanceRegistered(Long childId, String reportId) {
        boolean registered = scheduleRepository.findByChildIdAndDeletedAtIsNull(childId).isPresent();
        boolean notRequestedYet = reportRepository.findById(reportId).isEmpty();
        return AllowanceRegisteredResponse.builder()
                .registered(registered && notRequestedYet)
                .build();
    }

    @Override
    public ScheduleDto getSchedule(Long childId) {
        return ScheduleDto.from(scheduleRepository.findByChildIdAndDeletedAtIsNull(childId)
            .orElseThrow(() -> new BatchException(ScheduleErrorCodes.SCHEDULE_NOT_FOUND)));
    }
}
