package com.aicheck.chatbot.common.error;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FastApiErrorCodes implements ErrorCode {
	PERSUADE_API_ERROR(INTERNAL_SERVER_ERROR, "FAST_API_PERSUADE_API_ERROR_500", "persuade api에서 에러가 발생했습니다."),
	QUESTION_API_ERROR(INTERNAL_SERVER_ERROR, "FAST_API_QUESTION_API_ERROR_500", "question api에서 에러가 발생했습니다."),

	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
