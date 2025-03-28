package com.aicheck.alarm.application.service;

import java.util.List;

import com.aicheck.alarm.presentation.dto.response.AlarmResponse;

public interface AlarmService {
	List<AlarmResponse> searchAlarms(Long memberId);
	void readAlarm(Long alarmId, Long memberId);
	void deleteAlarm(Long alarmId, Long memberId);
}
