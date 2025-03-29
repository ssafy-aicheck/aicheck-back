package com.aicheck.batch.global.error;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;

public record ErrorResponse(
        String code,
        String message,
        LocalDateTime serverDateTime) {

    public ErrorResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), LocalDateTime.now());
    }

    public static ResponseEntity<ErrorResponse> of(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode));
    }
}
