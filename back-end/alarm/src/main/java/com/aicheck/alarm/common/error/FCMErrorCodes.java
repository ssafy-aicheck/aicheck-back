package com.aicheck.alarm.common.error;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FCMErrorCodes implements ErrorCode {
	FCM_SEND_FAILED(INTERNAL_SERVER_ERROR, "ALARM_FCM_SEND_FAILED_500", "FCM_SEND 에러가 발생했습니다."),

	;
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
