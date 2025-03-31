package com.aicheck.alarm.application.service;

public interface FCMTokenService {
	String searchFCMToken(Long memberId);
	void saveFCMToken(Long memberId, String token);
}
