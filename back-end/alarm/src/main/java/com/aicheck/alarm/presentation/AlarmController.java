package com.aicheck.alarm.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.alarm.application.service.AlarmService;
import com.aicheck.alarm.common.resolver.CurrentMemberId;
import com.aicheck.alarm.presentation.dto.request.DeleteAlarmRequest;
import com.aicheck.alarm.presentation.dto.request.ReadAlarmRequest;
import com.aicheck.alarm.presentation.dto.response.AlarmResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class AlarmController {

	private final AlarmService alarmService;

	@GetMapping
	public ResponseEntity<List<AlarmResponse>> findAlarms(@CurrentMemberId Long memberId) {
		return ResponseEntity.ok(alarmService.searchAlarms(memberId));
	}

	@PatchMapping
	public ResponseEntity<Void> readAlarm(@CurrentMemberId Long memberId,
		@Valid @RequestBody ReadAlarmRequest request) {
		alarmService.readAlarm(request.alarmId(), memberId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAlarm(@CurrentMemberId Long memberId,
		@Valid @RequestBody DeleteAlarmRequest request) {
		alarmService.deleteAlarm(request.alarmId(), memberId);
		return ResponseEntity.noContent().build();
	}
}
