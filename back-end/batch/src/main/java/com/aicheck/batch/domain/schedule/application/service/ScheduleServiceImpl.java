package com.aicheck.batch.domain.schedule.application.service;

import com.aicheck.batch.domain.schedule.application.client.BusinessClient;
import com.aicheck.batch.domain.schedule.application.client.dto.ChildAccountInfoResponse;
import com.aicheck.batch.domain.schedule.application.client.dto.ChildScheduleGroup;
import com.aicheck.batch.domain.schedule.application.client.dto.ChildScheduleItem;
import com.aicheck.batch.domain.schedule.application.client.dto.ScheduleListResponse;
import com.aicheck.batch.domain.schedule.dto.RegisterScheduledTransferRequest;
import com.aicheck.batch.domain.schedule.entity.Schedule;
import com.aicheck.batch.domain.schedule.repository.ScheduleRepository;
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
                            .map(s -> ChildScheduleItem.builder()
                                    .scheduleId(s.getId())
                                    .amount(s.getAmount().intValue())
                                    .interval(s.getInterval().name())
                                    .day(s.getStartDate().getDayOfWeek().name())
                                    .startDate(s.getStartDate())
                                    .build()
                            ).toList();

                    return ChildScheduleGroup.builder()
                            .childId(account.getMemberId())
                            .childName(account.getName())
                            .image(account.getImage())
                            .childAccountNo(account.getAccountNo())
                            .schedules(scheduleItems)
                            .build();
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

}
