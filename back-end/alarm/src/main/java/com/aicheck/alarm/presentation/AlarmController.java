package com.aicheck.alarm.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.alarm.application.service.AlarmService;
import com.aicheck.alarm.application.service.FCMTokenService;
import com.aicheck.alarm.common.resolver.CurrentMemberId;
import com.aicheck.alarm.presentation.dto.request.DeleteAlarmRequest;
import com.aicheck.alarm.presentation.dto.request.ReadAlarmRequest;
import com.aicheck.alarm.presentation.dto.request.SaveFCMTokenRequest;
import com.aicheck.alarm.presentation.dto.response.AlarmCountResponse;
import com.aicheck.alarm.presentation.dto.response.AlarmResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/alarm")
@RestController
public class AlarmController {

	private final AlarmService alarmService;
	private final FCMTokenService fcmTokenService;

	@GetMapping
	public ResponseEntity<List<AlarmResponse>> getAlarms(@CurrentMemberId final Long memberId) {
		return ResponseEntity.ok(alarmService.getAlarms(memberId));
	}

	@PatchMapping
	public ResponseEntity<Void> readAlarm(@CurrentMemberId final Long memberId,
		@Valid @RequestBody final ReadAlarmRequest request) {
		alarmService.readAlarm(request.alarmId(), memberId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAlarm(@CurrentMemberId final Long memberId,
		@Valid @RequestBody final DeleteAlarmRequest request) {
		alarmService.deleteAlarm(request.alarmId(), memberId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<Void> saveFCMToken(@Valid @RequestBody final SaveFCMTokenRequest request){
		fcmTokenService.saveFCMToken(request.memberId(), request.token());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/check")
	public ResponseEntity<AlarmCountResponse> getAlarmCounts(@CurrentMemberId final Long memberId){
		return ResponseEntity.ok(alarmService.getAlarmCounts(memberId));
	}

	@DeleteMapping("/fcm")
	public ResponseEntity<Void> deleteFCMToken(@CurrentMemberId final Long memberId) {
		fcmTokenService.deleteFCMToken(memberId);
		return ResponseEntity.noContent().build();
	}
}
