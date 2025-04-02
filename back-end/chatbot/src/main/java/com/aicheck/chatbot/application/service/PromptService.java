package com.aicheck.chatbot.application.service;

import com.aicheck.chatbot.presentation.prompt.dto.request.SavePromptRequest;
import com.aicheck.chatbot.presentation.prompt.dto.request.UpdatePromptRequest;
import com.aicheck.chatbot.presentation.prompt.dto.response.PromptResponse;

public interface PromptService {
	void savePrompt(SavePromptRequest request);
	PromptResponse searchPrompt(Long childId);
	PromptResponse updatePrompt(Long managerId, UpdatePromptRequest request);
}
