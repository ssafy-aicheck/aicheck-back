package com.aicheck.business.domain.auth.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.aicheck.business.domain.auth.application.client.dto.request.SavePromptRequest;

@FeignClient(name = "chatbot")
public interface ChatbotClient {

	@PostMapping("/prompts")
	void savePrompt(SavePromptRequest request);
}