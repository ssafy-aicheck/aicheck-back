package com.aicheck.batch.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScheduleErrorCodes implements ErrorCode {

    SCHEDULE_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE_MEMBER_NOT_FOUND_404", "찾을 수 없는 유저입니다"),
    DUPLICATED_SIGNUP(HttpStatus.CONFLICT, "SCHEDULE_DUPLICATED_SIGNUP_409", "중복된 회원가입입니다"),
    INCLUDED_NULL_VALUE(HttpStatus.BAD_REQUEST, "SCHEDULE_INCLUDED_NULL_400", "빈 값이 있습니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "SCHEDULE_INVALID_PASSWORD_401", "회원정보가 일치하지 않습니다"),

    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "SCHEDULE_JWT_INVALID_401", "유효하지 않은 JWT 서명입니다"),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "SCHEDULE_JWT_EXPIRED_401", "만료된 JWT 토큰입니다"),
    UNSUPPORTED_JWT(HttpStatus.BAD_REQUEST, "SCHEDULE_JWT_UNSUPPORTED_400", "지원하지 않는 JWT 토큰입니다"),
    MALFORMED_JWT(HttpStatus.BAD_REQUEST, "SCHEDULE_JWT_MALFORMED_400", "위조된 JWT 토큰입니다"),
    ILLEGAL_JWT(HttpStatus.BAD_REQUEST, "SCHEDULE_JWT_ILLEGAL_400", "잘못된 JWT 요청입니다"),

    NOT_FOUND_URL(HttpStatus.NOT_FOUND, "SCHEDULE_NOT_FOUND_URL_404", "존재하지 않는 URL입니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "SCHEDULE_METHOD_NOT_ALLOWED_405", "지원하지 않는 HTTP 메서드입니다"),
    
    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
