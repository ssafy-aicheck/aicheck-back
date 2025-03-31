package com.aicheck.alarm.infrastructure;

import com.aicheck.alarm.application.dto.AlarmRetryEventMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmRetryEventProducer {

	private static final String RETRY_TOPIC = "alarm-retry";
	private final KafkaTemplate<String, AlarmRetryEventMessage> kafkaTemplate;

	public void sendRetryMessage(AlarmRetryEventMessage message) {
		kafkaTemplate.send(RETRY_TOPIC, message.nextRetry());
	}
}