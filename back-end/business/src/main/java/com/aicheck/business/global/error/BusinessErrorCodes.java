package com.aicheck.business.global.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorCodes implements ErrorCode {

    BUSINESS_MEMBER_NOT_FOUND(NOT_FOUND, "BUSINESS_MEMBER_NOT_FOUND_404", "찾을 수 없는 유저입니다"),
    DUPLICATED_SIGNUP(CONFLICT, "BUSINESS_DUPLICATED_SIGNUP_409", "중복된 회원가입입니다"),
    INCLUDED_NULL_VALUE(BAD_REQUEST, "BUSINESS_INCLUDED_NULL_400", "빈 값이 있습니다"),
    INVALID_PASSWORD(UNAUTHORIZED, "BUSINESS_INVALID_PASSWORD_401", "회원정보가 일치하지 않습니다"),

    INVALID_JWT_SIGNATURE(UNAUTHORIZED, "BUSINESS_JWT_INVALID_401", "유효하지 않은 JWT 서명입니다"),
    EXPIRED_JWT(UNAUTHORIZED, "BUSINESS_JWT_EXPIRED_401", "만료된 JWT 토큰입니다"),
    UNSUPPORTED_JWT(BAD_REQUEST, "BUSINESS_JWT_UNSUPPORTED_400", "지원하지 않는 JWT 토큰입니다"),
    MALFORMED_JWT(BAD_REQUEST, "BUSINESS_JWT_MALFORMED_400", "위조된 JWT 토큰입니다"),
    ILLEGAL_JWT(BAD_REQUEST, "BUSINESS_JWT_ILLEGAL_400", "잘못된 JWT 요청입니다"),

    NOT_FOUND_URL(NOT_FOUND, "BUSINESS_NOT_FOUND_URL_404", "존재하지 않는 URL입니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "BUSINESS_METHOD_NOT_ALLOWED_405", "지원하지 않는 HTTP 메서드입니다"),

    MAIN_ACCOUNT_NOT_SET(BAD_REQUEST, "BUSINESS_MAIN_ACCOUNT_NOT_SET_400", "대표 계좌가 설정되지 않았습니다"),
    ALLOWANCE_REQUEST_ALREADY_EXIST(BAD_REQUEST, "BUSINESS_ALLOWANCE_REQUEST_ALREADY_EXIST_400",
            "용돈 인상 요청이 이미 존재합니다"),
    ALLOWANCE_REQUEST_NOT_FOUND(NOT_FOUND, "BUSINESS_ALLOWANCE_REQUEST_NOT_FOUND_404",
            "존재하지 않는 용돈 인상 요청입니다"),
    INVALID_RESPOND_STATUS(BAD_REQUEST, "BUSINESS_INVALID_RESPOND_STATUS_400", "유효하지 않은 응답코드입니다"),
    ALREADY_DECIDED_ALLOWANCE_REQUEST(BAD_REQUEST, "BUSINESS_ALLOWANCE_ALREADY_DECIDED_400", "이미 결정된 요청입니다"),

    NOT_YOUR_CHILD(BAD_REQUEST, "BUSINESS_TRANSACTION_NOT_YOUR_CHILD", "자신의 자녀만 조회할 수 있습니다"),

    TRANSACTION_RECORD_NOT_FOUND(NOT_FOUND, "BUSINESS_TRANSACTION_RECORD_NOT_FOUND", "해당 기록을 찾을 수 없습니다"),
    FIRST_CATEGORY_NOT_FOUND(NOT_FOUND, "BUSINESS_FIRST_CATEGORY_NOT_FOUND", "해당 대분류 카테고리를 찾을 수 없습니다"),
    SECOND_CATEGORY_NOT_FOUND_OR_INVALID(BAD_REQUEST, "BUSINESS_SECOND_CATEGORY_NOT_FOUND_OR_INVALID",
            "해당 소분류 카테고리가 존재하지 않거나 대분류에 속하지 않습니다"),

    NOT_FOUND_ALLOWANCE_REQUEST(NOT_FOUND, "BUSINESS_NOT_FOUND_ALLOWANCE_REQUEST_404", "해당하는 용돈 요청을 찾을 수 없습니다."),
    UNAUTHORIZED_UPDATE_ALLOWANCE_REQUEST_STATUS(UNAUTHORIZED,
            "BUSINESS_UNAUTHORIZED_UPDATE_ALLOWANCE_REQUEST_STATUS_401", "용돈 요청을 응답할 권한이 없습니다."),

    MAIL_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "MAIL_SERVER_EXCEPTION_503", "메일 서버가 불안정합니다"),
    WRONG_MAIL_CODE(BAD_REQUEST, "WRONG_AUTHENTICATION_MAIL_CODE_400", "인증번호가 일치하지 않습니다");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
