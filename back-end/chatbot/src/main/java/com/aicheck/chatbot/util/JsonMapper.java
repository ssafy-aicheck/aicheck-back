package com.aicheck.chatbot.util;

import static com.aicheck.chatbot.common.error.ChatbotErrorCodes.TYPE_MISMATCH;

import java.util.List;

import org.springframework.stereotype.Component;

import com.aicheck.chatbot.common.exception.ChatbotException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JsonMapper {

	private final ObjectMapper objectMapper;

	public <T> String toJson(T obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new ChatbotException(TYPE_MISMATCH);
		}
	}

	public <T> List<T> fromJsonList(String json, Class<T> clazz) {
		try {
			JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
			return objectMapper.readValue(json, listType);
		} catch (JsonProcessingException e) {
			throw new ChatbotException(TYPE_MISMATCH);
		}
	}
}