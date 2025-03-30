package com.aicheck.alarm.application.listener;

import com.aicheck.alarm.application.dto.AlarmRetryEventMessage;
import com.aicheck.alarm.application.service.FCMService;
import com.aicheck.alarm.common.exception.FCMException;
import com.aicheck.alarm.infrastructure.AlarmRetryEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AlarmRetryEventListener {

	private static final int MAX_RETRY_COUNT = 3;

	private final FCMService fcmService;
	private final AlarmRetryEventProducer retryEventProducer;

	@KafkaListener(
		topics = "alarm-retry",
		groupId = "alarm-retry-group",
		containerFactory = "retryKafkaListenerContainerFactory"
	)
	public void onRetryMessage(final AlarmRetryEventMessage message, final Acknowledgment ack) {
		if (message.retryCount() >= MAX_RETRY_COUNT) {
			log.error("최대 재시도 초과 - token={}, title={}, retryCount={}",
				message.token(), message.title(), message.retryCount());
			ack.acknowledge();
			return;
		}

		try {
			fcmService.sendNotification(message.token(), message.title(), message.body());
			ack.acknowledge();
		} catch (FCMException e) {
			log.warn("[FCM 재시도 실패] token={}, retryCount={}, reason={}",
				message.token(), message.retryCount(), e.getMessage(), e);
			retryEventProducer.sendRetryMessage(message);
			ack.acknowledge();
		} catch (Exception e) {
			log.error("[Unknown Error - Retry] message={}, error={}", message, e.getMessage(), e);
		}
	}
}