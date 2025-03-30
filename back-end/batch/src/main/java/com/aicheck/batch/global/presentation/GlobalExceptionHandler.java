package com.aicheck.batch.global.presentation;

import com.aicheck.batch.global.error.BatchException;
import com.aicheck.batch.global.error.ErrorCode;
import com.aicheck.batch.global.error.ErrorResponse;
import com.aicheck.batch.global.error.ScheduleErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BatchException.class)
    public ResponseEntity<ErrorResponse> handleBatchException(BatchException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ErrorResponse.of(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException() {
        ErrorCode errorCode = ScheduleErrorCodes.INCLUDED_NULL_VALUE;
        return ErrorResponse.of(errorCode);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handle404(NoHandlerFoundException ex) {
        ScheduleErrorCodes errorCode = ScheduleErrorCodes.NOT_FOUND_URL;

        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        ErrorCode errorCode = ScheduleErrorCodes.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode));
    }


}
