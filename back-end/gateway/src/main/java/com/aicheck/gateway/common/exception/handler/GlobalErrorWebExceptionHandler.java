package com.aicheck.gateway.common.exception.handler;

import static com.aicheck.gateway.common.error.GatewayErrorCodes.*;

import com.aicheck.gateway.common.error.ErrorResponse;
import com.aicheck.gateway.common.error.GatewayErrorCodes;
import com.aicheck.gateway.util.ObjectMapperUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
@Order(-1)
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		GatewayErrorCodes errorCode = INTERNAL_SERVER_ERROR;

		if (ex instanceof ResponseStatusException responseStatusException) {
			status = (HttpStatus)responseStatusException.getStatusCode();
			errorCode = switch (status) {
				case NOT_FOUND -> NOT_FOUND_URL;
				case METHOD_NOT_ALLOWED -> METHOD_NOT_ALLOWED;
				case BAD_REQUEST -> INVALID_REQUEST;
				default -> INTERNAL_SERVER_ERROR;
			};
		}

		exchange.getResponse().setStatusCode(status);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		ErrorResponse errorResponse = ErrorResponse.of(errorCode).getBody();
		String json = ObjectMapperUtil.toJson(errorResponse);
		byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

		return exchange.getResponse().writeWith(Mono.just(buffer));
	}
}