package com.aicheck.alarm.application.service;

import java.util.List;

import com.aicheck.alarm.application.dto.AlarmEventMessage;
import com.aicheck.alarm.presentation.dto.response.AlarmResponse;

public interface AlarmService {
	List<AlarmResponse> getAlarms(Long memberId);
	void readAlarm(Long alarmId, Long memberId);
	void deleteAlarm(Long alarmId, Long memberId);
	void saveAlarm(AlarmEventMessage message);
}
