package com.aicheck.chatbot.infrastructure.client.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aicheck.chatbot.common.error.FeignErrorDecoder;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;

@Configuration
@EnableFeignClients(basePackages = "com.aicheck.chatbot.infrastructure.client.*")
public class FeignConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("Content-Type", "application/json");
			requestTemplate.header("Accept", "application/json");
		};
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignErrorDecoder();
	}
}
