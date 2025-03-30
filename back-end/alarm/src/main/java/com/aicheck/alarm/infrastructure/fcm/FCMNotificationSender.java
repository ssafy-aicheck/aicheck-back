package com.aicheck.alarm.infrastructure.fcm;

import static com.aicheck.alarm.common.error.FCMErrorCodes.FCM_SEND_FAILED;

import com.aicheck.alarm.application.port.NotificationSender;
import com.aicheck.alarm.common.exception.FCMException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FCMNotificationSender implements NotificationSender {

	@Override
	public void send(String token, String title, String body) {
		final Notification notification = Notification.builder()
			.setTitle(title)
			.setBody(body)
			.build();

		final Message message = Message.builder()
			.setToken(token)
			.setNotification(notification)
			.build();

		try {
			FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			throw new FCMException(FCM_SEND_FAILED);
		}
	}
}