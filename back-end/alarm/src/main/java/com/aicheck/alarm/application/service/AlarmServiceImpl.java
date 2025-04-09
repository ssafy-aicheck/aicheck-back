package com.aicheck.alarm.application.service;

import static com.aicheck.alarm.common.error.AlarmErrorCodes.NOT_FOUND_ALARM;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.alarm.application.dto.AlarmEventMessage;
import com.aicheck.alarm.common.exception.AlarmException;
import com.aicheck.alarm.domain.Alarm;
import com.aicheck.alarm.domain.repository.AlarmRepository;
import com.aicheck.alarm.presentation.dto.response.AlarmCountResponse;
import com.aicheck.alarm.presentation.dto.response.AlarmResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AlarmServiceImpl implements AlarmService {

	private final AlarmRepository alarmRepository;

	@Override
	public List<AlarmResponse> getAlarms(final Long memberId) {
		return alarmRepository.findAllByMemberIdAndDeletedAtIsNullOrderByCreatedAtDesc(memberId)
			.stream()
			.map(AlarmResponse::from)
			.toList();
	}

	@Transactional
	@Override
	public void readAlarm(final Long alarmId, final Long memberId) {
		final Alarm alarm = alarmRepository.findByIdAndMemberIdAndDeletedAtIsNull(alarmId, memberId)
			.orElseThrow(() -> new AlarmException(NOT_FOUND_ALARM));
		alarm.changeRead(true);
	}

	@Transactional
	@Override
	public void deleteAlarm(final Long memberId, final Long alarmId) {
		final Alarm alarm = alarmRepository.findByIdAndMemberIdAndDeletedAtIsNull(alarmId, memberId)
			.orElseThrow(() -> new AlarmException(NOT_FOUND_ALARM));
		alarmRepository.delete(alarm);
	}

	@Transactional
	@Override
	public void saveAlarm(final AlarmEventMessage message) {
		alarmRepository.save(message.toEntity());
	}

	@Override
	public AlarmCountResponse getAlarmCounts(Long memberId) {
		return AlarmCountResponse.of(alarmRepository.countByMemberIdAndIsReadFalseAndDeletedAtIsNull(memberId));
	}
}
