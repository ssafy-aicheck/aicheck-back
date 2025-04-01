package com.aicheck.alarm.config;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.aicheck.alarm.application.dto.AlarmEventMessage;
import com.aicheck.alarm.application.dto.AlarmRetryEventMessage;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	private static final String TRUSTED_PACKAGES = "com.aicheck.*";

	private <T> ConsumerFactory<String, T> consumerFactory(String groupId, Class<T> clazz) {
		JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz);
		deserializer.addTrustedPackages(TRUSTED_PACKAGES);

		Map<String, Object> config = new HashMap<>();
		config.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(GROUP_ID_CONFIG, groupId);
		config.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
		config.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
		config.put(ENABLE_AUTO_COMMIT_CONFIG, false);
		config.put(ISOLATION_LEVEL_CONFIG, "read_committed");

		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
	}

	private <T> ConcurrentKafkaListenerContainerFactory<String, T> factory(String groupId, Class<T> clazz) {
		var factory = new ConcurrentKafkaListenerContainerFactory<String, T>();
		factory.setConsumerFactory(consumerFactory(groupId, clazz));
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
		return factory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, AlarmEventMessage> kafkaListenerContainerFactory() {
		return factory("alarm-group", AlarmEventMessage.class);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, AlarmRetryEventMessage> retryKafkaListenerContainerFactory() {
		return factory("alarm-retry-group", AlarmRetryEventMessage.class);
	}
}