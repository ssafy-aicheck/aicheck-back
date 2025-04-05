package com.aicheck.chatbot.infrastructure.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.aicheck.chatbot.infrastructure.event.dto.request.AlarmEventMessage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlarmEventProducer {

	private static final String TOPIC = "alarm";
	private final KafkaTemplate<String, AlarmEventMessage> kafkaTemplate;

	public void sendEvent(final AlarmEventMessage message) {
		kafkaTemplate.send(TOPIC, message);
	}
}
