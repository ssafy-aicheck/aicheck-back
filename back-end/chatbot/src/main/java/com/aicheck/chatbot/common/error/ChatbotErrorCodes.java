package com.aicheck.chatbot.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatbotErrorCodes implements ErrorCode {
	INCLUDED_NULL_VALUE(BAD_REQUEST, "CHATBOT_INCLUDED_NULL_400", "필수 값이 누락되었습니다"),
	INVALID_REQUEST(BAD_REQUEST, "CHATBOT_INVALID_REQUEST_400", "잘못된 요청입니다"),
	TYPE_MISMATCH(INTERNAL_SERVER_ERROR, "CHATBOT_TYPE_MISMATCH_500", "타입 변환에 실패했습니다."),
	NOT_FOUND_PROMPT(BAD_REQUEST, "CHATBOT_NOT_FOUND_PROMPT_400", "prompt가 존재하지 않습니다."),
	UNAUTHORIZED_CHANGE_PROMPT(UNAUTHORIZED, "CHATBOT_UNAUTHORIZED_CHANGE_PROMPT_401", "prompt를 변경할 권한이 없습니다."),
	NOT_FOUND_MANAGER_ID(INTERNAL_SERVER_ERROR, "CHATBOT_NOT_FOUND_MANAGER_ID_500", "childId에 해당하는 managerId가 없습니다."),

	;
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
