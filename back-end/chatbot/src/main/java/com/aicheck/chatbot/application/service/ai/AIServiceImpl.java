package com.aicheck.chatbot.application.service.ai;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aicheck.chatbot.infrastructure.client.fastApi.dto.request.AIRequest;
import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.PersuadeResponse;
import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.QuestionResponse;
import com.aicheck.chatbot.domain.chat.ChatNode;
import com.aicheck.chatbot.domain.chat.CustomSetting;
import com.aicheck.chatbot.infrastructure.client.fastApi.FastApiClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AIServiceImpl implements AIService {

	private final FastApiClient fastApiClient;

	@Override
	public PersuadeResponse sendPersuadeChat(final CustomSetting customSetting, final List<ChatNode> chatHistories,
		final String message) {
		return fastApiClient.callPersuadeApi(AIRequest.from(customSetting, chatHistories, message));
	}

	@Override
	public QuestionResponse sendQuestionChat(final CustomSetting customSetting, final List<ChatNode> chatHistories,
		final String message) {
		return fastApiClient.callQuestionApi(AIRequest.from(customSetting, chatHistories, message));
	}
}
