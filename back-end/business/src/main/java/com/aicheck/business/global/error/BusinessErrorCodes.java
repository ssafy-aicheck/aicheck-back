package com.aicheck.business.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorCodes implements ErrorCode {

    BUSINESS_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "BUSINESS_MEMBER_NOT_FOUND_404", "찾을 수 없는 유저입니다"),
    DUPLICATED_SIGNUP(HttpStatus.CONFLICT, "BUSINESS_DUPLICATED_SIGNUP_409", "중복된 회원가입입니다"),
    INCLUDED_NULL_VALUE(HttpStatus.BAD_REQUEST, "BUSINESS_INCLUDED_NULL_400", "빈 값이 있습니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "BUSINESS_INVALID_PASSWORD_401", "회원정보가 일치하지 않습니다"),

    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "BUSINESS_JWT_INVALID_401", "유효하지 않은 JWT 서명입니다"),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "BUSINESS_JWT_EXPIRED_401", "만료된 JWT 토큰입니다"),
    UNSUPPORTED_JWT(HttpStatus.BAD_REQUEST, "BUSINESS_JWT_UNSUPPORTED_400", "지원하지 않는 JWT 토큰입니다"),
    MALFORMED_JWT(HttpStatus.BAD_REQUEST, "BUSINESS_JWT_MALFORMED_400", "위조된 JWT 토큰입니다"),
    ILLEGAL_JWT(HttpStatus.BAD_REQUEST, "BUSINESS_JWT_ILLEGAL_400", "잘못된 JWT 요청입니다"),

    NOT_FOUND_URL(HttpStatus.NOT_FOUND, "BUSINESS_NOT_FOUND_URL_404", "존재하지 않는 URL입니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "BUSINESS_METHOD_NOT_ALLOWED_405", "지원하지 않는 HTTP 메서드입니다"),

    MAIN_ACCOUNT_NOT_SET(HttpStatus.BAD_REQUEST, "BUSINESS_MAIN_ACCOUNT_NOT_SET_400", "대표 계좌가 설정되지 않았습니다"),
    ALLOWANCE_REQUEST_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "BUSINESS_ALLOWANCE_REQUEST_ALREADY_EXIST_400",
            "용돈 인상 요청이 이미 존재합니다"),
    ALLOWANCE_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "BUSINESS_ALLOWANCE_REQUEST_NOT_FOUND_404",
            "존재하지 않는 용돈 인상 요청입니다"),
    INVALID_RESPOND_STATUS(HttpStatus.BAD_REQUEST, "BUSINESS_INVALID_RESPOND_STATUS_400", "유효하지 않은 응답코드입니다"),
    ALREADY_DECIDED_ALLOWANCE_REQUEST(HttpStatus.BAD_REQUEST, "BUSINESS_ALLOWANCE_ALREADY_DECIDED_400", "이미 결정된 요청입니다"),

    NOT_YOUR_CHILD(HttpStatus.BAD_REQUEST, "BUSINESS_TRANSACTION_NOT_YOUR_CHILD", "자신의 자녀만 조회할 수 있습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
