package com.aicheck.alarm.infrastructure.fcm;

public interface NotificationSender {
	void send(String token, String title, String body);
}