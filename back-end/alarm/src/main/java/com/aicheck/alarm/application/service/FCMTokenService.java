package com.aicheck.alarm.application.service;

public interface FCMTokenService {
	String getFCMToken(Long memberId);
	void saveFCMToken(Long memberId, String token);
}
