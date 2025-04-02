package com.aicheck.chatbot.application.service.chatbot;

import static com.aicheck.chatbot.domain.chat.Type.PERSUADE;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.chatbot.application.service.ai.AIService;
import com.aicheck.chatbot.application.service.ai.dto.response.AIChatResponse;
import com.aicheck.chatbot.application.service.prompt.PromptService;
import com.aicheck.chatbot.application.service.prompt.dto.response.PromptInfo;
import com.aicheck.chatbot.application.service.redis.RedisService;
import com.aicheck.chatbot.application.service.redis.dto.request.AIMessage;
import com.aicheck.chatbot.application.service.redis.dto.request.CustomSettingRequest;
import com.aicheck.chatbot.application.service.redis.dto.request.MemberMessage;
import com.aicheck.chatbot.domain.chat.ChatNode;
import com.aicheck.chatbot.domain.chat.CustomSetting;
import com.aicheck.chatbot.domain.chat.Type;
import com.aicheck.chatbot.infrastructure.client.business.BatchFeignClient;
import com.aicheck.chatbot.infrastructure.client.business.dto.response.MemberInfo;
import com.aicheck.chatbot.presentation.chatbot.dto.response.ChatResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatbotServiceImpl implements ChatbotService {

	private final PromptService promptService;
	private final RedisService redisService;
	private final BatchFeignClient batchFeignClient;
	private final AIService aiService;

	@Override
	public ChatResponse sendChat(final Long childId, final Type type, final String message) {
		final CustomSetting customSetting = redisService.loadCustomSetting(childId);
		final List<ChatNode> chatHistories = redisService.loadChatHistory(childId, type);
		final AIChatResponse aiChatResponse;

		if(type.equals(PERSUADE)){
			aiChatResponse = aiService.sendPersuadeChat(customSetting, chatHistories, message);
		}else{
			aiChatResponse = aiService.sendQuestionChat(customSetting, chatHistories, message);
		}

		if(aiChatResponse.isPersuaded()){
			//TODO 용돈 요청 생성 로직
			//TODO 알림 전송 로직
			endChat(childId, type);
		}else{
			redisService.appendChatHistory(
				childId, type, AIMessage.of(aiChatResponse.message()), MemberMessage.of(message));
		}
		return ChatResponse.from(aiChatResponse);
	}

	@Override
	public void startChat(final Long memberId, final Type type) {
		final PromptInfo promptInfo = promptService.getPrompt(memberId);
		final MemberInfo memberInfo = batchFeignClient.getMemberInfo(memberId);
		redisService.storeCustomSetting(CustomSettingRequest.of(memberId, promptInfo, memberInfo));
		redisService.initChatHistory(memberId, type);
	}

	@Override
	public void endChat(final Long childId, final Type type) {
		redisService.removeCustomSetting(childId);
		redisService.removeChatHistory(childId, type);
	}
}
