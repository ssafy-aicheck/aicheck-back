package com.aicheck.chatbot.application.service.chatbot;

import com.aicheck.chatbot.domain.chat.Type;
import com.aicheck.chatbot.presentation.chatbot.dto.response.ChatResponse;

public interface ChatbotService {
	ChatResponse sendChat(Long childId, Type type, String message);
	void startChat(Long childId, Type type);
	void endChat(Long childId, Type type);
}
