package com.aicheck.alarm.application.listener;

import com.aicheck.alarm.application.dto.AlarmEventMessage;
import com.aicheck.alarm.application.dto.AlarmRetryEventMessage;
import com.aicheck.alarm.application.service.AlarmService;
import com.aicheck.alarm.application.service.FCMTokenService;
import com.aicheck.alarm.common.exception.AlarmException;
import com.aicheck.alarm.common.exception.FCMException;
import com.aicheck.alarm.infrastructure.AlarmRetryEventProducer;
import com.aicheck.alarm.infrastructure.fcm.NotificationSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AlarmEventListener {

	private final AlarmService alarmService;
	private final FCMTokenService fcmTokenService;
	private final NotificationSender notificationSender;
	private final AlarmRetryEventProducer producer;

	@KafkaListener(
		topics = "alarm",
		groupId = "alarm-group",
		containerFactory = "kafkaListenerContainerFactory"
	)
	public void onMessage(final AlarmEventMessage message, final Acknowledgment ack) {
		String token = null;
		try {
			token = fcmTokenService.getFCMToken(message.memberId());
			alarmService.saveAlarm(message);
			notificationSender.send(token, message.title(), message.body());
			ack.acknowledge();
		} catch (AlarmException e) {
			log.error("[Alarm 저장 실패] memberId={}, reason={}", message.memberId(), e.getMessage(), e);
		} catch (FCMException e) {
			log.warn("[FCM 전송 실패 - 재시도 요청] memberId={}, title={}, reason={}",
				message.memberId(), message.title(), e.getMessage(), e);
			producer.sendRetryMessage(AlarmRetryEventMessage.of(message, token));
			ack.acknowledge();
		} catch (Exception e) {
			log.error("[Unknown Error] message={}, error={}", message, e.getMessage(), e);
		}
	}
}