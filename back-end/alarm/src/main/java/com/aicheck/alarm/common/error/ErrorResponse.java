package com.aicheck.alarm.common.error;

import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        String message,
        LocalDateTime serverDateTime) {

    public ErrorResponse(final ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), LocalDateTime.now());
    }
}
