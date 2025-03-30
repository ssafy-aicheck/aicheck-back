package com.aicheck.alarm.application.service;

import static com.aicheck.alarm.common.error.AlarmErrorCodes.NOT_FOUND_FCM_TOKEN;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.alarm.common.exception.AlarmException;
import com.aicheck.alarm.domain.repository.FCMTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FCMTokenServiceImpl implements FCMTokenService {

	private final FCMTokenRepository fcmTokenRepository;

	@Override
	public String searchFCMToken(final Long memberId) {
		return fcmTokenRepository.findByMemberIdAndDeletedAtIsNull(memberId)
			.orElseThrow(()-> new AlarmException(NOT_FOUND_FCM_TOKEN)).getToken();
	}
}
