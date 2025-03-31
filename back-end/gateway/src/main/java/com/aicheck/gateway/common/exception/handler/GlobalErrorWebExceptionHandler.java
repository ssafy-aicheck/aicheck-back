package com.aicheck.gateway.common.exception.handler;

import com.aicheck.gateway.common.error.ErrorResponse;
import com.aicheck.gateway.common.error.GatewayErrorCodes;
import com.aicheck.gateway.util.ErrorResponseWriter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@Order(-1)
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		GatewayErrorCodes errorCode = GatewayErrorCodes.INTERNAL_SERVER_ERROR;
		int status = 500;

		if (ex instanceof ResponseStatusException responseStatusException) {
			status = responseStatusException.getStatusCode().value();
			errorCode = switch (status) {
				case 404 -> GatewayErrorCodes.NOT_FOUND_URL;
				case 405 -> GatewayErrorCodes.METHOD_NOT_ALLOWED;
				case 400 -> GatewayErrorCodes.INVALID_REQUEST;
				default -> GatewayErrorCodes.INTERNAL_SERVER_ERROR;
			};
		}

		return ErrorResponseWriter.write(
			exchange,
			new ErrorResponse(errorCode),
			status
		);
	}
}