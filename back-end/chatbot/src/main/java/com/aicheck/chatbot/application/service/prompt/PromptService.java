package com.aicheck.chatbot.application.service.prompt;

import com.aicheck.chatbot.application.service.prompt.dto.response.PromptInfo;
import com.aicheck.chatbot.presentation.prompt.dto.request.SavePromptRequest;
import com.aicheck.chatbot.presentation.prompt.dto.request.UpdatePromptRequest;

public interface PromptService {
	void savePrompt(SavePromptRequest request);
	PromptInfo getPrompt(Long childId);
	PromptInfo updatePrompt(Long managerId, UpdatePromptRequest request);
}
