package com.aicheck.alarm.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCodes implements ErrorCode {
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "COMMON_INTERNAL_SERVER_ERROR_500", "내부 서버 오류"),
    NOT_FOUND_URL(NOT_FOUND, "COMMON_NOT_FOUND_URL_404", "존재하지 않는 URL"),
    INVALID_JSON_DATA(BAD_REQUEST, "COMMON_INVALID_JSON_DATA_400", "잘못된 형식의 JSON DATA"),
    INVALID_HEADER_DATA(BAD_REQUEST, "COMMON_INVALID_HEADER_DATA_400", "잘못된 형식의 Header DATA"),
    MESSAGE_EXTRACTION_FAILED(INTERNAL_SERVER_ERROR, "COMMON_MESSAGE_EXTRACTION_FAILED_500", "에러 메시지 추출 중 문제가 발생했습니다"),
    BAD_METHOD(METHOD_NOT_ALLOWED, "COMMON_BAD_METHOD_405", "잘못된 형식의 Method 입니다."),

    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}