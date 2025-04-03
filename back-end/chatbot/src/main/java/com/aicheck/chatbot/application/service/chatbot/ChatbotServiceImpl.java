package com.aicheck.chatbot.application.service.chatbot;

import static com.aicheck.chatbot.domain.chat.ChatType.PERSUADE;
import static com.aicheck.chatbot.domain.chat.ChatType.QUESTION;
import static com.aicheck.chatbot.domain.chat.Judge.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(readOnly = true)
@Service
public class ChatbotServiceImpl implements ChatbotService {

	private final PromptService promptService;
	private final RedisService redisService;
	private final BatchFeignClient batchFeignClient;
	private final BusinessFeignClient businessFeignClient;
	private final AIService aiService;
	private final AlarmEventProducer alarmEventProducer;

	@Override
	public PersuadeChatResponse sendPersuadeChat(Long childId, String message) {
		final CustomSetting customSetting = redisService.loadCustomSetting(childId);
		final List<ChatNode> chatHistories = redisService.loadChatHistory(childId, PERSUADE);
		final PromptInfo promptInfo = promptService.getPrompt(childId);

		final PersuadeResponse persuadeResponse = aiService.sendPersuadeChat(customSetting, chatHistories, message);
		if (persuadeResponse.isPersuaded()) {
			//TODO 용돈 요청 생성 로직
			Long endPointId = businessFeignClient.saveAllowanceRequest(
				SaveAllowanceRequest.of(childId, promptInfo.managerId(), persuadeResponse.result()));
			//TODO 알림 전송 로직
			alarmEventProducer.sendEvent(AlarmEventMessage.of(promptInfo.managerId(), persuadeResponse.result().title(),
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
	public void startChat(final Long childId, final ChatType chatType) {
		final PromptInfo promptInfo = promptService.getPrompt(childId);
		final ScheduledAllowance scheduledAllowance = batchFeignClient.getScheduledAllowance(childId);
		final TransactionInfoResponse transactionInfo = businessFeignClient
			.getTransactionInfo(childId, scheduledAllowance.startDate(), scheduledAllowance.interval());

		redisService.storeCustomSetting(
			CustomSettingRequest.of(promptInfo, scheduledAllowance.allowance(), transactionInfo));
		redisService.initChatHistory(childId, chatType);
	}

	@Override
	public void endChat(final Long childId, final ChatType chatType) {
		redisService.removeCustomSetting(childId);
		redisService.removeChatHistory(childId, chatType);
	}
}
