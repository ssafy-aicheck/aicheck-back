package com.aicheck.chatbot.application.service.redis;

import static java.lang.Boolean.*;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.chatbot.application.service.redis.dto.request.AIMessage;
import com.aicheck.chatbot.application.service.redis.dto.request.CustomSettingRequest;
import com.aicheck.chatbot.application.service.redis.dto.request.MemberMessage;
import com.aicheck.chatbot.domain.chat.ChatNode;
import com.aicheck.chatbot.domain.chat.ChatType;
import com.aicheck.chatbot.domain.chat.CustomSetting;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RedisServiceImpl implements RedisService {
	private static final String SETTING_KEY_PREFIX = "chat:setting:";
	private static final String HISTORY_KEY_PREFIX = "chat:history:";

	private final RedisTemplate<String, CustomSetting> customSettingRedisTemplate;
	private final RedisTemplate<String, ChatNode> chatNodeRedisTemplate;

	@Override
	@Transactional
	public void storeCustomSetting(final CustomSettingRequest request) {
		customSettingRedisTemplate.opsForValue().set(settingKey(request.childId()), CustomSetting.from(request));
	}

	@Transactional
	@Override
	public void removeCustomSetting(Long childId) {
		customSettingRedisTemplate.delete(settingKey(childId));
	}

	@Override
	public CustomSetting loadCustomSetting(Long childId) {
		return customSettingRedisTemplate.opsForValue().get(settingKey(childId));
	}

	@Override
	@Transactional
	public void initChatHistory(Long childId, ChatType chatType) {
		String key = historyKey(chatType, childId);
		if (FALSE.equals(chatNodeRedisTemplate.hasKey(key))) {
			chatNodeRedisTemplate.opsForList().rightPushAll(key);
		}
	}

	@Transactional
	@Override
	public void appendChatHistory(
		final Long childId, final ChatType chatType, final AIMessage aiMessage, final MemberMessage memberMessage) {
		final String key = historyKey(chatType, childId);

		chatNodeRedisTemplate.opsForList().rightPush(key, ChatNode.builder()
			.role(memberMessage.role())
			.message(memberMessage.message())
			.build());

		chatNodeRedisTemplate.opsForList().rightPush(key, ChatNode.builder()
			.role(aiMessage.role())
			.message(aiMessage.message())
			.build());
	}

	@Transactional
	@Override
	public void removeChatHistory(Long childId, ChatType chatType) {
		chatNodeRedisTemplate.delete(historyKey(chatType, childId));
	}

	@Override
	public List<ChatNode> loadChatHistory(Long childId, ChatType chatType) {
		return chatNodeRedisTemplate.opsForList().range(historyKey(chatType, childId), 0, -1);
	}

	private String settingKey(Long childId) {
		return SETTING_KEY_PREFIX + childId;
	}

	private String historyKey(ChatType chatType, Long childId) {
		return HISTORY_KEY_PREFIX + chatType.name() + ":" + childId;
	}
}
