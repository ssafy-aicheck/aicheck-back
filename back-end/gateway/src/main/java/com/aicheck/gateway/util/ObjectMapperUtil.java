package com.aicheck.gateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapperUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return "{\"status\":500,\"message\":\"JSON 직렬화 오류\"}";
		}
	}
}