package com.aicheck.business.global.presentation;

import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.global.error.BusinessErrorCodes;
import com.aicheck.business.global.error.ErrorCode;
import com.aicheck.business.global.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ErrorResponse.of(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException() {
        ErrorCode errorCode = BusinessErrorCodes.INCLUDED_NULL_VALUE;
        return ErrorResponse.of(errorCode);
    }

}
