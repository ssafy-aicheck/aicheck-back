package com.aicheck.alarm.application.service;

import static com.aicheck.alarm.common.error.AlarmErrorCodes.NOT_FOUND_FCM_TOKEN;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.alarm.common.entity.BaseEntity;
import com.aicheck.alarm.common.exception.AlarmException;
import com.aicheck.alarm.domain.FCMToken;
import com.aicheck.alarm.domain.repository.FCMTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FCMTokenServiceImpl implements FCMTokenService {

	private final FCMTokenRepository fcmTokenRepository;

	@Override
	public String getFCMToken(final Long memberId) {
		return fcmTokenRepository.findByMemberId(memberId)
			.orElseThrow(() -> new AlarmException(NOT_FOUND_FCM_TOKEN)).getToken();
	}

	@Transactional
	@Override
	public void saveFCMToken(final Long memberId, final String token) {
		fcmTokenRepository.findByMemberId(memberId)
			.ifPresentOrElse(
				fcm -> fcm.changeToken(token),
				() -> fcmTokenRepository.save(FCMToken.builder().memberId(memberId).token(token).build())
			);
	}

	@Transactional
	@Override
	public void deleteFCMToken(Long memberId) {
		fcmTokenRepository.deleteByMemberId(memberId);
	}
}
