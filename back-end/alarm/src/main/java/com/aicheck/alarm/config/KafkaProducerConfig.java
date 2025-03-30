package com.aicheck.alarm.config;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.aicheck.alarm.application.dto.AlarmRetryEventMessage;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public ProducerFactory<String, AlarmRetryEventMessage> alarmRetryProducerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, AlarmRetryEventMessage> alarmRetryKafkaTemplate() {
		return new KafkaTemplate<>(alarmRetryProducerFactory());
	}
}