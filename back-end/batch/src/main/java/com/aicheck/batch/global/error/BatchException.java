package com.aicheck.batch.global.error;

import lombok.Getter;

@Getter
public class BatchException extends RuntimeException {
    private final ErrorCode errorCode;

    public BatchException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

