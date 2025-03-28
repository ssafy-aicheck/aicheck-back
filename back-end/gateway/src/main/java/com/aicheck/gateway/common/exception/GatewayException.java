package com.aicheck.gateway.common.exception;

import com.aicheck.gateway.common.error.ErrorCode;

public class GatewayException extends AicheckException {
	public GatewayException(ErrorCode errorCode) {
		super(errorCode);
	}
}