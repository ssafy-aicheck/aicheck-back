package com.aicheck.alarm.application.port;

public interface NotificationSender {
	void send(String token, String title, String body);
}