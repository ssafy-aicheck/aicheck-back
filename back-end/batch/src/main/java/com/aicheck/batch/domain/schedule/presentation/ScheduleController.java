package com.aicheck.batch.domain.schedule.presentation;

import com.aicheck.batch.domain.schedule.application.client.dto.ChildScheduleItem;
import com.aicheck.batch.domain.schedule.application.client.dto.ScheduleListResponse;
import com.aicheck.batch.domain.schedule.application.service.ScheduleService;
import com.aicheck.batch.domain.schedule.dto.RegisterScheduledTransferRequest;
import com.aicheck.batch.domain.schedule.presentation.dto.AllowanceRegisteredResponse;
import com.aicheck.batch.global.auth.annotation.CurrentMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> createSchedule(@CurrentMemberId Long memberId,
                                               @RequestBody RegisterScheduledTransferRequest request) {
        scheduleService.createSchedule(memberId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ScheduleListResponse> getSchedule(@CurrentMemberId Long memberId) {
        ScheduleListResponse response = scheduleService.getSchedules(memberId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@CurrentMemberId Long memberId,
                                               @PathVariable Long scheduleId,
                                               @RequestBody RegisterScheduledTransferRequest request) {
        scheduleService.updateSchedule(memberId, scheduleId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<ChildScheduleItem> getSchedulesByChildId(@PathVariable Long childId) {
        ChildScheduleItem response = scheduleService.findByChildId(childId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check")
    public ResponseEntity<AllowanceRegisteredResponse> checkAllowanceSchedule(@CurrentMemberId Long memberId) {
        AllowanceRegisteredResponse response = scheduleService.checkAllowanceRegistered(memberId);
        return ResponseEntity.ok(response);
    }

}
