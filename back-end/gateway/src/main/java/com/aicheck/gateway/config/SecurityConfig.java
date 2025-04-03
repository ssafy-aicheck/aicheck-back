package com.aicheck.gateway.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;

import com.aicheck.gateway.common.exception.handler.CustomAccessDeniedHandler;
import com.aicheck.gateway.common.exception.handler.CustomAuthenticationEntryPoint;
import com.aicheck.gateway.security.filter.JwtAuthenticationFilter;
import com.aicheck.gateway.security.jwt.JwtProvider;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

	private final JwtProvider jwtProvider;

	private static final String[] PUBLIC_PATHS = {
		"/aicheck/auth/signup",
		"/aicheck/auth/signin",
		"/aicheck/auth/email",
		"/aicheck/auth/email/check",
		"/swagger-ui/**",
		"/swagger-ui.html",
		"/aicheck/v3/api-docs",
		"/alarm/v3/api-docs",
		"/chatbot/v3/api-docs",
	};

	@Bean
	@Order(1)
	public SecurityWebFilterChain publicChain(ServerHttpSecurity http) {
		http
			.securityMatcher(new OrServerWebExchangeMatcher(
				ServerWebExchangeMatchers.pathMatchers(PUBLIC_PATHS)
			))
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.authorizeExchange(exchange -> exchange.anyExchange().permitAll());
		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityWebFilterChain securedChain(ServerHttpSecurity http) {
		http
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.authorizeExchange(exchange -> exchange

				// actuator 관련
				.pathMatchers("/actuator/**").permitAll()

				// swagger 관련
				.pathMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

				// 인증 관련
				.pathMatchers(POST, "/aicheck/auth/reissue").authenticated()

				// 회원
				.pathMatchers(GET, "/aicheck/members/children/profiles").hasRole(Role.PARENT)
				.pathMatchers(GET, "/aicheck/members/details").authenticated()
				.pathMatchers(PATCH, "/aicheck/members/details").authenticated()

				// 계좌
				.pathMatchers(GET, "/aicheck/accounts").authenticated()
				.pathMatchers(GET, "/aicheck/accounts/{childId}").authenticated()
				.pathMatchers(POST, "/aicheck/accounts").authenticated()
				.pathMatchers(GET, "/aicheck/accounts/my").authenticated()
				.pathMatchers(POST, "/aicheck/accounts/check").authenticated()
				.pathMatchers(GET, "/aicheck/accounts/children").hasRole(Role.PARENT)
				.pathMatchers(GET, "/aicheck/accounts/description-ratio").authenticated()
				.pathMatchers(GET, "/aicheck/accounts/children/internal/**").permitAll()
				.pathMatchers(GET, "/aicheck/accounts/number/{memberId}").permitAll()
				.pathMatchers(GET, "/aicheck/accounts/sender/**").permitAll()
				.pathMatchers(GET, "/aicheck/accounts/receiver/**").permitAll()

				// 정기 송금
				.pathMatchers(GET, "/batch/schedules").hasRole(Role.PARENT)
				.pathMatchers(POST, "/batch/schedules").hasRole(Role.PARENT)
				.pathMatchers(PATCH, "/batch/schedules/{scheduleId}").hasRole(Role.PARENT)
				.pathMatchers(DELETE, "/batch/schedules/{scheduleId}").hasRole(Role.PARENT)
				.pathMatchers(GET, "/batch/schedules/check").hasRole(Role.CHILD)
				.pathMatchers(GET, "/batch/schedules/child/{childId}").permitAll()

				// 송금
				.pathMatchers(GET, "/aicheck/transfer/{accountNo}").authenticated()
				.pathMatchers(POST, "/aicheck/transfer").authenticated()

				// 리포트
				.pathMatchers(GET, "/aicheck/reports/**").authenticated()

				// 용돈 요청
				.pathMatchers(GET, "/aicheck/allowance").authenticated()
				.pathMatchers(POST, "/aicheck/allowance").hasRole(Role.PARENT)
				.pathMatchers(GET, "/aicheck/allowance/details/{id}").authenticated()
				.pathMatchers(POST, "/aicheck/allowance/increase").hasRole(Role.CHILD)
				.pathMatchers(POST, "/aicheck/allowance/increase/{id}").hasRole(Role.PARENT)
				.pathMatchers(GET, "/aicheck/allowance/increase/details/{id}").authenticated()
				.pathMatchers(GET, "/aicheck/allowance/summary").hasRole(Role.CHILD)

				// 채팅
				.pathMatchers(POST, "/chatbot/start").hasRole(Role.CHILD)
				.pathMatchers(POST, "/chatbot/persuade").hasRole(Role.CHILD)
				.pathMatchers(POST, "/chatbot/question").hasRole(Role.CHILD)
				.pathMatchers(DELETE, "/chatbot/end").hasRole(Role.CHILD)
				.pathMatchers(PATCH, "/chatbot/prompts").hasRole(Role.PARENT)
				.pathMatchers(GET, "/chatbot/prompts/{childId}").hasRole(Role.PARENT)

				// 피싱
				.pathMatchers(GET, "/aicheck/phishings").authenticated()
				.pathMatchers(GET, "/aicheck/phishings/family").authenticated()

				// 알림
				.pathMatchers(GET, "/alarm").authenticated()
				.pathMatchers(PATCH, "/alarm").authenticated()
				.pathMatchers(DELETE, "/alarm").authenticated()

				// 카테고리
				.pathMatchers(GET, "/aicheck/category/**").authenticated()

				// 금전출납부
				.pathMatchers(GET, "/aicheck/transaction-records/**").authenticated()
				.pathMatchers(GET, "/aicheck/transaction-records/child/**").hasRole(Role.PARENT)
				.pathMatchers(PATCH, "/aicheck/transaction-records").authenticated()
				.pathMatchers(POST, "/aicheck/transaction-records/dutch-pays").authenticated()
				.pathMatchers(POST, "/aicheck/transaction-records/rating").hasRole(Role.PARENT)

				.anyExchange().denyAll())
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				.accessDeniedHandler(new CustomAccessDeniedHandler())
			)
			.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), SecurityWebFiltersOrder.AUTHENTICATION);
		return http.build();
	}

	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(List.of("http://localhost:3000", "http://j12a603.p.ssafy.io"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setExposedHeaders(List.of("Authorization", "X-User-ID", "X-User-Role"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsWebFilter(source);
	}
	private static class Role {
		static final String PARENT = "PARENT";
		static final String CHILD = "CHILD";
	}
}