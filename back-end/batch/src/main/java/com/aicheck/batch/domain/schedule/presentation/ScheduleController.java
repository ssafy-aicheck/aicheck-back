package com.aicheck.batch.domain.schedule.presentation;

import com.aicheck.batch.domain.schedule.application.client.dto.ScheduleListResponse;
import com.aicheck.batch.domain.schedule.application.service.ScheduleService;
import com.aicheck.batch.domain.schedule.dto.RegisterScheduledTransferRequest;
import com.aicheck.batch.global.auth.annotation.CurrentMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody RegisterScheduledTransferRequest request,
                                               @RequestHeader("Authorization") String authorizationHeader) {
        scheduleService.createSchedule(request, authorizationHeader);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ScheduleListResponse> getSchedule(@CurrentMemberId Long memberId) {
        ScheduleListResponse response = scheduleService.getSchedules(memberId);
        return ResponseEntity.ok(response);
    }

}
