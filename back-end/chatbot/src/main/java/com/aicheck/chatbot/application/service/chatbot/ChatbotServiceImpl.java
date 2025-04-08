package com.aicheck.chatbot.application.service.chatbot;

import static com.aicheck.chatbot.domain.chat.ChatType.PERSUADE;
import static com.aicheck.chatbot.domain.chat.ChatType.QUESTION;
import static com.aicheck.chatbot.domain.chat.Judge.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aicheck.chatbot.application.service.ai.AIService;
import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.PersuadeResponse;
import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.QuestionResponse;
import com.aicheck.chatbot.application.service.prompt.PromptService;
import com.aicheck.chatbot.application.service.prompt.dto.response.PromptInfo;
import com.aicheck.chatbot.application.service.redis.RedisService;
import com.aicheck.chatbot.application.service.redis.dto.request.AIMessage;
import com.aicheck.chatbot.application.service.redis.dto.request.CustomSettingRequest;
import com.aicheck.chatbot.application.service.redis.dto.request.MemberMessage;
import com.aicheck.chatbot.domain.chat.ChatNode;
import com.aicheck.chatbot.domain.chat.CustomSetting;
import com.aicheck.chatbot.domain.chat.ChatType;
import com.aicheck.chatbot.infrastructure.client.batch.BatchFeignClient;
import com.aicheck.chatbot.infrastructure.client.batch.dto.response.ScheduledAllowance;
import com.aicheck.chatbot.infrastructure.client.business.BusinessFeignClient;
import com.aicheck.chatbot.infrastructure.client.business.dto.request.SaveAllowanceRequest;
import com.aicheck.chatbot.infrastructure.client.business.dto.response.TransactionInfoResponse;
import com.aicheck.chatbot.infrastructure.event.AlarmEventProducer;
import com.aicheck.chatbot.infrastructure.event.dto.request.AlarmEventMessage;
import com.aicheck.chatbot.presentation.chatbot.dto.response.PersuadeChatResponse;
import com.aicheck.chatbot.presentation.chatbot.dto.response.QuestionChatResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatbotServiceImpl implements ChatbotService {

	private final PromptService promptService;
	private final RedisService redisService;
	private final AIService aiService;
	private final BatchFeignClient batchFeignClient;
	private final BusinessFeignClient businessFeignClient;
	private final AlarmEventProducer alarmEventProducer;

	@Override
	public PersuadeChatResponse sendPersuadeChat(Long childId, String message) {
		final CustomSetting customSetting = redisService.loadCustomSetting(childId);
		final List<ChatNode> chatHistories = redisService.loadChatHistory(childId, PERSUADE);

		final PersuadeResponse persuadeResponse = aiService.sendPersuadeChat(customSetting, chatHistories, message);
		if (persuadeResponse.isPersuaded()) {
			final Long managerId = promptService.getPrompt(childId).managerId();
			final Long endPointId = businessFeignClient.saveAllowanceRequest(
				SaveAllowanceRequest.from(childId, managerId, persuadeResponse.result()));

			alarmEventProducer.sendEvent(AlarmEventMessage.of(managerId, persuadeResponse.result().title(),
				persuadeResponse.result().description(), endPointId));

			endChat(childId, PERSUADE);
		} else {
			redisService.appendChatHistory(
				childId, PERSUADE, AIMessage.of(persuadeResponse.message()), MemberMessage.of(message));
		}
		return PersuadeChatResponse.from(persuadeResponse);
	}

	@Override
	public QuestionChatResponse sendQuestionChat(Long childId, String message) {
		final CustomSetting customSetting = redisService.loadCustomSetting(childId);
		final List<ChatNode> chatHistories = redisService.loadChatHistory(childId, QUESTION);

		final QuestionResponse questionResponse = aiService.sendQuestionChat(customSetting, chatHistories, message);

		if(questionResponse.judge().equals(JUDGING)) {
			redisService.appendChatHistory(
				childId, QUESTION, AIMessage.of(questionResponse.message()), MemberMessage.of(message));
			return QuestionChatResponse.from(questionResponse);
		}

		endChat(childId, QUESTION);
		return QuestionChatResponse.from(questionResponse);
	}

	@Override
	public void startChat(Long childId, ChatType chatType) {
		final PromptInfo promptInfo = promptService.getPrompt(childId);
		ScheduledAllowance scheduledAllowance = null;
		TransactionInfoResponse transactionInfo = null;

		try{
			scheduledAllowance = batchFeignClient.getScheduledAllowance(childId);
			transactionInfo = businessFeignClient.getTransactionInfo(
				childId,
				scheduledAllowance.startDate(),
				scheduledAllowance.interval().name()
			);
		}catch (Exception e){
			scheduledAllowance = null;
			transactionInfo = null;
		}

		final CustomSetting setting = CustomSetting.from(
			CustomSettingRequest.from(promptInfo, scheduledAllowance, transactionInfo)
		);
		redisService.prepareChatSession(childId, chatType, setting);
	}

	@Override
	public void endChat(Long childId, ChatType chatType) {
		redisService.clearChatSession(childId, chatType);
	}
}