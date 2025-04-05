package com.aicheck.chatbot.common.exception;

import com.aicheck.chatbot.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class FastApiException extends RuntimeException {
	private final ErrorCode errorCode;

	public FastApiException(final ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
