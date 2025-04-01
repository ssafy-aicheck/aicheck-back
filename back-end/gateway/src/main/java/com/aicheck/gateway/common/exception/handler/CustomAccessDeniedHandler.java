package com.aicheck.gateway.common.exception.handler;

import com.aicheck.gateway.common.error.ErrorResponse;
import com.aicheck.gateway.common.error.GatewayErrorCodes;
import com.aicheck.gateway.util.ErrorResponseWriter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
		return ErrorResponseWriter.write(
			exchange,
			new ErrorResponse(GatewayErrorCodes.ACCESS_DENIED),
			403
		);
	}
}