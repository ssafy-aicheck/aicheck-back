package com.aicheck.chatbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebClientConfig {

	@Value("${fast-api.base-url}")
	private String fastApiBaseUrl;

	@Bean(name = "fastApiWebClient")
	public WebClient fastApiWebClient() {
		return WebClient.builder()
			.baseUrl(fastApiBaseUrl)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}
}