package com.aicheck.bank.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BankErrorCodes implements ErrorCode {

    BANK_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "BANK_MEMBER_404", "은행에 등록되지 않은 유저입니다"),
    INCLUDED_NULL_VALUE(HttpStatus.BAD_REQUEST, "BANK_INCLUDED_NULL_400", "빈 값이 있습니다"),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "BANK_ACCOUNT_404", "찾을 수 없는 계좌입니다"),
    INVALID_PASSWORD(HttpStatus.FORBIDDEN, "BANK_INVALID_PASSWORD_403", "계좌 비밀번호가 다릅니다"),

    NOT_YOUR_ACCOUNT(HttpStatus.FORBIDDEN, "BANK_NOT_YOUR_ACCOUNT_403", "본인 계좌만 등록 가능합니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
