package com.aicheck.alarm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@OpenAPIDefinition(
	info = @Info(title = "API Document", description = "", version = "v3")
)
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().addServersItem(new Server().url("/"));
	}
}
