package com.aicheck.gateway.security.filter;

import com.aicheck.gateway.common.error.ErrorResponse;
import com.aicheck.gateway.common.error.GatewayErrorCodes;
import com.aicheck.gateway.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FrontendOriginFilter implements WebFilter {

	private final Environment environment;

	@Value("${trusted.origins:}")
	private List<String> trustedOrigins;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String activeProfile = environment.getProperty("spring.profiles.active", "dev");

		if (activeProfile.equals("dev")) {
			return chain.filter(exchange);
		}

		String origin = exchange.getRequest().getHeaders().getOrigin();
		String referer = exchange.getRequest().getHeaders().getFirst("Referer");

		boolean allowed = (origin == null && referer == null) || trustedOrigins.stream().anyMatch(trust ->
			trust.equals(origin) || (referer != null && referer.startsWith(trust)));

		if (!allowed) {
			log.warn("차단된 Origin 요청: origin={}, referer={}", origin, referer);
			exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
			exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

			ErrorResponse error = ErrorResponse.of(GatewayErrorCodes.ACCESS_DENIED).getBody();
			byte[] bytes = ObjectMapperUtil.toJson(error).getBytes(StandardCharsets.UTF_8);
			DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
			return exchange.getResponse().writeWith(Mono.just(buffer));
		}

		return chain.filter(exchange);
	}
}
