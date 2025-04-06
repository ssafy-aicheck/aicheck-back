package com.aicheck.alarm.common.exception.handler;

import static com.aicheck.alarm.common.error.AlarmErrorCodes.INCLUDED_NULL_VALUE;
import static com.aicheck.alarm.common.error.AlarmErrorCodes.INVALID_REQUEST;
import static com.aicheck.alarm.common.error.GlobalErrorCodes.MESSAGE_EXTRACTION_FAILED;
import static com.aicheck.alarm.common.error.GlobalErrorCodes.SERVER_ERROR;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aicheck.alarm.common.exception.AlarmException;
import com.aicheck.alarm.common.error.ErrorCode;
import com.aicheck.alarm.common.error.ErrorResponse;
import com.aicheck.alarm.common.exception.FCMException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlarmException.class)
    public ResponseEntity<ErrorResponse> handleAlarmException(final AlarmException e) {
        return buildResponse(e.getErrorCode());
    }

    @ExceptionHandler(FCMException.class)
    public ResponseEntity<ErrorResponse> handleFCMException(final FCMException e) {
        return buildResponse(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final String errorMessage = e.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getDefaultMessage())
            .findFirst()
            .orElseThrow(() -> new AlarmException(MESSAGE_EXTRACTION_FAILED));
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(INCLUDED_NULL_VALUE.getCode(), errorMessage, LocalDateTime.now()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(final ConstraintViolationException e) {
        final String errorMessage = e.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .findFirst()
            .orElseThrow(() -> new AlarmException(MESSAGE_EXTRACTION_FAILED));
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(INVALID_REQUEST.getCode(), errorMessage, LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final Exception e) {
        return buildResponse(SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildResponse(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(new ErrorResponse(errorCode));
    }
}