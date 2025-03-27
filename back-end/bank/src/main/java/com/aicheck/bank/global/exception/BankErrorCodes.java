package com.aicheck.bank.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BankErrorCodes implements ErrorCode {

    BANK_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "BANK_MEMBER_404", "은행에 등록되지 않은 유저입니다"),
    INCLUDED_NULL_VALUE(HttpStatus.BAD_REQUEST, "BANK_INCLUDED_NULL_400", "빈 값이 있습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
