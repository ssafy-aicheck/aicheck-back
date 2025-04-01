package com.aicheck.gateway.common.exception;

import com.aicheck.gateway.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class AicheckException extends RuntimeException {
	private final ErrorCode errorCode;

	public AicheckException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
