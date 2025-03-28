package com.aicheck.gateway.common.exception.handler;

import static com.aicheck.gateway.common.error.GatewayErrorCodes.*;
import static com.aicheck.gateway.util.ObjectMapperUtil.*;

import com.aicheck.gateway.common.error.ErrorResponse;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		ErrorResponse errorResponse = ErrorResponse.of(INVALID_LOGIN_TOKEN).getBody();
		String json = toJson(errorResponse);
		byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

		return exchange.getResponse().writeWith(Mono.just(buffer));
	}
}