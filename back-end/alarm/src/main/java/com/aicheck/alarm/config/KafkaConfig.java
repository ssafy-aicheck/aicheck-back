package com.aicheck.alarm.config;

import com.aicheck.alarm.application.dto.AlarmEventMessage;
import com.aicheck.alarm.application.dto.AlarmRetryEventMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	private static final String TRUSTED_PACKAGES = "com.aicheck.*";

	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		Map<String, Object> config = Map.of(
			ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
			ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
			ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
		);
		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	private <T> ConsumerFactory<String, T> consumerFactory(String groupId, Class<T> valueType) {
		JsonDeserializer<T> deserializer = new JsonDeserializer<>(valueType);
		deserializer.addTrustedPackages(TRUSTED_PACKAGES);

		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		config.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
	}

	private <T> ConcurrentKafkaListenerContainerFactory<String, T> listenerFactory(String groupId, Class<T> valueType) {
		ConcurrentKafkaListenerContainerFactory<String, T> factory =
			new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory(groupId, valueType));
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
		return factory;
	}

	@Bean(name = "kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, AlarmEventMessage> alarmListenerFactory() {
		return listenerFactory("alarm-group", AlarmEventMessage.class);
	}

	@Bean(name = "retryKafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, AlarmRetryEventMessage> retryListenerFactory() {
		return listenerFactory("alarm-retry-group", AlarmRetryEventMessage.class);
	}
}