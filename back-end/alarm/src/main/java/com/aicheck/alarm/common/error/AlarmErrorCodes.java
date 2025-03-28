package com.aicheck.alarm.common.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmErrorCodes implements ErrorCode {

	INCLUDED_NULL_VALUE(HttpStatus.BAD_REQUEST, "ALARM_INCLUDED_NULL_400", "필수 값이 누락되었습니다"),
	INVALID_REQUEST(HttpStatus.BAD_REQUEST, "ALARM_INVALID_REQUEST_400", "잘못된 요청입니다"),
	NOT_FOUND_URL(HttpStatus.NOT_FOUND, "ALARM_NOT_FOUND_URL_404", "존재하지 않는 URL입니다"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "ALARM_METHOD_NOT_ALLOWED_405", "허용되지 않은 HTTP 메서드입니다");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}