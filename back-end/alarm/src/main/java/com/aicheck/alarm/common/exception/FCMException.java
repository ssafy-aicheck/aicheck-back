package com.aicheck.alarm.common.exception;

import com.aicheck.alarm.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class FCMException extends RuntimeException {
	private final ErrorCode errorCode;

	public FCMException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
