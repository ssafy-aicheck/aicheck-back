package com.aicheck.alarm.common.exception;

import com.aicheck.alarm.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class AlarmException extends RuntimeException {
	private final ErrorCode errorCode;

	public AlarmException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
