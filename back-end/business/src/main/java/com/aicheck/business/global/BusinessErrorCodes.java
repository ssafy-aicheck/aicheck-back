package com.aicheck.business.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorCodes implements ErrorCode {

    BANK_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "BANK_MEMBER_404", "찾을 수 없는 유저입니다"),
    DUPLICATED_SIGNUP(HttpStatus.CONFLICT, "DUPLICATED_SIGNUP_409", "중복된 회원가입입니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
