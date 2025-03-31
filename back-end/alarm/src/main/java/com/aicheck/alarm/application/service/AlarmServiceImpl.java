package com.aicheck.alarm.application.service;

import static com.aicheck.alarm.common.error.AlarmErrorCodes.NOT_FOUND_ALARM;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.alarm.application.dto.AlarmEventMessage;
import com.aicheck.alarm.common.exception.AlarmException;
import com.aicheck.alarm.domain.Alarm;
import com.aicheck.alarm.domain.repository.AlarmRepository;
import com.aicheck.alarm.presentation.dto.response.AlarmResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AlarmServiceImpl implements AlarmService {

	private final AlarmRepository alarmRepository;

	@Override
	public List<AlarmResponse> searchAlarms(Long memberId) {
		return alarmRepository.findAllByMemberIdAndDeletedAtIsNull(memberId)
			.stream()
			.map(AlarmResponse::from)
			.toList();
	}

	@Transactional
	@Override
	public void readAlarm(Long alarmId, Long memberId) {
		Alarm alarm = alarmRepository.findByIdAndMemberIdAndDeletedAtIsNull(alarmId, memberId)
			.orElseThrow(() -> new AlarmException(NOT_FOUND_ALARM));
		alarm.changeRead(true);
	}

	@Transactional
	@Override
	public void deleteAlarm(Long memberId, Long alarmId) {
		Alarm alarm = alarmRepository.findByIdAndMemberIdAndDeletedAtIsNull(alarmId, memberId)
			.orElseThrow(() -> new AlarmException(NOT_FOUND_ALARM));
		alarmRepository.delete(alarm);
	}

	@Transactional
	@Override
	public void saveAlarm(AlarmEventMessage message) {
		alarmRepository.save(Alarm.builder()
			.memberId(message.memberId())
			.title(message.title())
			.body(message.body())
			.type(message.type())
			.endPointId(message.endPointId())
			.build());
	}
}
