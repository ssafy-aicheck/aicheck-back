package com.aicheck.gateway.util;

import static org.springframework.http.HttpStatus.*;

import com.aicheck.gateway.common.error.ErrorResponse;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class ErrorResponseWriter {

	public Mono<Void> write(ServerWebExchange exchange, ErrorResponse errorResponse, int statusCode) {
		exchange.getResponse().setStatusCode(valueOf(statusCode));
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		String json = ObjectMapperUtil.toJson(errorResponse);
		byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

		return exchange.getResponse().writeWith(Mono.just(buffer));
	}
}