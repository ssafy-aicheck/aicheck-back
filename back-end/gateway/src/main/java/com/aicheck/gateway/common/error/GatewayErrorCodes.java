package com.aicheck.gateway.common.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GatewayErrorCodes implements ErrorCode {
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GATEWAY_INTERNAL_SERVER_ERROR_500", "내부 서버 오류"),
	NOT_FOUND_URL(HttpStatus.NOT_FOUND, "GATEWAY_NOT_FOUND_URL_404", "존재하지 않는 URL"),
	INVALID_JSON_DATA(HttpStatus.BAD_REQUEST, "GATEWAY_INVALID_JSON_DATA_400", "잘못된 형식의 JSON DATA"),
	INVALID_HEADER_DATA(HttpStatus.BAD_REQUEST, "GATEWAY_INVALID_HEADER_DATA_400", "잘못된 형식의 Header DATA"),
	INVALID_LOGIN_TOKEN(HttpStatus.UNAUTHORIZED, "GATEWAY_INVALID_LOGIN_TOKEN_401", "토큰 정보가 유효하지 않습니다"),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "GATEWAY_ACCESS_DENIED_403", "잘못된 권한 요청입니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "GATEWAY_TOKEN_EXPIRED_401", "토큰이 만료되었습니다"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "GATEWAY_METHOD_NOT_ALLOWED_405", "허용되지 않은 HTTP 메서드입니다."),
	INVALID_REQUEST(HttpStatus.BAD_REQUEST, "GATEWAY_INVALID_REQUEST_400", "잘못된 요청입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
