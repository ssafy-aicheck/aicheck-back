package com.aicheck.gateway.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.aicheck.gateway.common.error.GatewayErrorCodes;
import com.aicheck.gateway.common.exception.GatewayException;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

	@Value("${jwt.secretKey}")
	private String secretKey;

	private Key key;

	@PostConstruct
	public void init() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public Claims getClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		}catch (ExpiredJwtException e) {
			throw new GatewayException(GatewayErrorCodes.TOKEN_EXPIRED);
		} catch (JwtException e) {
			throw new GatewayException(GatewayErrorCodes.INVALID_LOGIN_TOKEN);
		}
	}
}