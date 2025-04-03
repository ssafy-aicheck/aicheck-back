package com.aicheck.batch.domain.schedule.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.batch.domain.schedule.application.service.ScheduleService;
import com.aicheck.batch.domain.schedule.presentation.dto.ScheduledAllowanceResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ScheduleChatbotController {

	private final ScheduleService scheduleService;

	@GetMapping("/{childId}")
	ResponseEntity<ScheduledAllowanceResponse> getScheduledAllowance(@PathVariable Long childId){
		return ResponseEntity.ok(ScheduledAllowanceResponse.from(scheduleService.getSchedule(childId)));
	}
}
