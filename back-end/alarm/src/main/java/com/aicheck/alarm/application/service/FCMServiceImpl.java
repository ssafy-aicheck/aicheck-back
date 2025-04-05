package com.aicheck.alarm.application.service;

import com.aicheck.alarm.infrastructure.fcm.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FCMServiceImpl implements FCMService {

	private final NotificationSender notificationSender;

	public void sendNotification(final String token, final String title, final String body) {
		notificationSender.send(token, title, body);
	}
}