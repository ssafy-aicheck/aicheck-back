package com.aicheck.business.config;

import com.aicheck.business.domain.auth.application.service.JwtProvider;
import com.aicheck.business.domain.auth.filter.AuthExceptionHandlerFilter;
import com.aicheck.business.domain.auth.filter.CustomAuthenticationEntryPoint;
import com.aicheck.business.domain.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> {
                            auth.requestMatchers("/auth/**",
                                "/v3/api-docs/**",          // OpenAPI 문서 경로
                                "/swagger-ui/**",           // Swagger UI 자원
                                "/swagger-ui.html"          // Swagger 진입점
                                ).permitAll();
                            auth.requestMatchers(HttpMethod.OPTIONS).permitAll();
                            auth.anyRequest().authenticated();
                        }
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthExceptionHandlerFilter(), JwtAuthenticationFilter.class);
        http.exceptionHandling(manager ->
                manager.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtProvider jwtProvider) {
        return new JwtAuthenticationFilter(jwtProvider);
    }

}
