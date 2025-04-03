package com.aicheck.chatbot.application.service.chatbot;

import com.aicheck.chatbot.domain.chat.ChatType;
import com.aicheck.chatbot.presentation.chatbot.dto.response.PersuadeChatResponse;
import com.aicheck.chatbot.presentation.chatbot.dto.response.QuestionChatResponse;

public interface ChatbotService {
	PersuadeChatResponse sendPersuadeChat(Long childId, String message);
	QuestionChatResponse sendQuestionChat(Long childId, String message);
	void startChat(Long childId, ChatType chatType);
	void endChat(Long childId, ChatType chatType);
}
