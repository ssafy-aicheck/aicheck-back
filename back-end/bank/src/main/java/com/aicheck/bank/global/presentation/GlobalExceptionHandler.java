package com.aicheck.bank.global.presentation;

import com.aicheck.bank.global.exception.BankException;
import com.aicheck.bank.global.exception.ErrorCode;
import com.aicheck.bank.global.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankException.class)
    public ResponseEntity<ErrorResponse> handleBankException(BankException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ErrorResponse.of(errorCode);
    }

}
