package com.aicheck.gateway.common.exception.handler;

import com.aicheck.gateway.common.error.ErrorResponseDto;
import com.aicheck.gateway.common.error.GlobalErrorCodes;
import com.aicheck.gateway.util.ObjectMapperUtil;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, org.springframework.security.access.AccessDeniedException ex) {
		exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		ErrorResponseDto errorResponse = ErrorResponseDto.of(GlobalErrorCodes.ACCESS_DENIED).getBody();
		String json = ObjectMapperUtil.toJson(errorResponse);
		byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

		return exchange.getResponse().writeWith(Mono.just(buffer));
	}
}