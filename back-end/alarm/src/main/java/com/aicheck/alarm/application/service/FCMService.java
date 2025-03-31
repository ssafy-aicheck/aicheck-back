package com.aicheck.alarm.application.service;

import com.aicheck.alarm.application.port.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FCMService {

	private final NotificationSender notificationSender;

	public void sendNotification(String token, String title, String body) {
		notificationSender.send(token, title, body);
	}
}