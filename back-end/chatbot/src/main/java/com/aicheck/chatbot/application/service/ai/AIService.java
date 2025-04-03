package com.aicheck.chatbot.application.service.ai;

import java.util.List;

import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.PersuadeResponse;
import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.QuestionResponse;
import com.aicheck.chatbot.domain.chat.ChatNode;
import com.aicheck.chatbot.domain.chat.CustomSetting;

public interface AIService {
	PersuadeResponse sendPersuadeChat(CustomSetting customSetting, List<ChatNode> chatHistories, String message);
	QuestionResponse sendQuestionChat(CustomSetting customSetting, List<ChatNode> chatHistories, String message);
}
