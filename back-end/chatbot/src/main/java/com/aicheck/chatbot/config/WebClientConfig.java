package com.aicheck.chatbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebClientConfig {

	@Bean
	public WebClient fastApiWebClient() {
		return WebClient.builder()
			.baseUrl("https://7c54-211-192-210-7.ngrok-free.app")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}
}