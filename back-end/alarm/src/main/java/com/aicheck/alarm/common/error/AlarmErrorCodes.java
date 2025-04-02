package com.aicheck.alarm.common.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmErrorCodes implements ErrorCode {

	INCLUDED_NULL_VALUE(BAD_REQUEST, "ALARM_INCLUDED_NULL_400", "필수 값이 누락되었습니다"),
	INVALID_REQUEST(BAD_REQUEST, "ALARM_INVALID_REQUEST_400", "잘못된 요청입니다"),
	NOT_FOUND_ALARM(BAD_REQUEST, "ALARM_NOT_FOUND_ALARM_400", "존재하지 않는 alarm 입니다."),
	NOT_FOUND_FCM_TOKEN(INTERNAL_SERVER_ERROR, "ALARM_NOT_FOUND_FCM_TOKEN_500", "FCM_Token이 존재하지 않습니다."),

	;
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}