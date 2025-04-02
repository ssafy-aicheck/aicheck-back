package com.aicheck.chatbot.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatbotErrorCodes implements ErrorCode {
	INCLUDED_NULL_VALUE(BAD_REQUEST, "CHATBOT_INCLUDED_NULL_400", "필수 값이 누락되었습니다"),
	INVALID_REQUEST(BAD_REQUEST, "CHATBOT_INVALID_REQUEST_400", "잘못된 요청입니다"),

	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
