package com.aicheck.gateway.common.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aicheck.gateway.common.error.ErrorResponse;
import com.aicheck.gateway.common.error.GatewayErrorCodes;
import com.aicheck.gateway.common.exception.AicheckException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(AicheckException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(AicheckException e) {
		return ErrorResponse.of(e.getErrorCode());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		return ErrorResponse.of(GatewayErrorCodes.INTERNAL_SERVER_ERROR);
	}
}
