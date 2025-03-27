package com.aicheck.gateway.security.filter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.aicheck.gateway.common.error.GlobalErrorCodes;
import com.aicheck.gateway.common.exception.GatewayException;
import com.aicheck.gateway.security.jwt.JwtProvider;
import com.aicheck.gateway.util.HeaderUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {
	private static final String USER_ID_HEADER = "X-User-ID";
	private static final String USER_ROLE_HEADER = "X-User-Role";
	private final JwtProvider jwtProvider;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String token = HeaderUtil.getToken(exchange);
		if (token == null || token.isBlank()) {
			return chain.filter(exchange);
		}

		Claims claims = jwtProvider.getClaims(token);
		String subject = claims.getSubject();
		if (subject == null || subject.isBlank()) {
			throw new GatewayException(GlobalErrorCodes.INVALID_LOGIN_TOKEN);
		}

		Long userId;
		try {
			userId = Long.valueOf(subject);
		} catch (NumberFormatException e) {
			throw new GatewayException(GlobalErrorCodes.INVALID_LOGIN_TOKEN);
		}

		String authString = claims.get("auth", String.class);
		if (authString == null || authString.isBlank()) {
			throw new GatewayException(GlobalErrorCodes.INVALID_LOGIN_TOKEN);
		}

		List<GrantedAuthority> authorities = Arrays.stream(authString.split(","))
			.map(String::trim)
			.filter(role -> !role.isEmpty())
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());

		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(userId, null, authorities);

		ServerWebExchange modifiedExchange = exchange.mutate()
			.request(builder -> builder
				.header(USER_ID_HEADER, String.valueOf(userId))
				.header(USER_ROLE_HEADER, authString)
			)
			.build();

		return chain.filter(modifiedExchange)
			.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
	}
}