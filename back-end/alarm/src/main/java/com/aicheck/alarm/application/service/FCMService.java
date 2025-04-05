package com.aicheck.alarm.application.service;

public interface FCMService {
	void sendNotification(String token, String title, String body);
}
