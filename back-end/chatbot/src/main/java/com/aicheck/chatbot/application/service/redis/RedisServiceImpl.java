package com.aicheck.chatbot.application.service.redis;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.chatbot.application.service.redis.dto.request.AIMessage;
import com.aicheck.chatbot.application.service.redis.dto.request.CustomSettingRequest;
import com.aicheck.chatbot.application.service.redis.dto.request.MemberMessage;
import com.aicheck.chatbot.domain.chat.ChatNode;
import com.aicheck.chatbot.domain.chat.CustomSetting;
import com.aicheck.chatbot.domain.chat.Type;

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
		final CustomSetting setting = CustomSetting.builder()
			.originalAllowance(request.originalAllowance())
			.conversationStyle(request.conversationStyle())
			.age(request.age())
			.gender(request.gender())
			.averageScore(request.averageScore())
			.categoryDifficulties(request.categoryDifficulties())
			.build();

		final String key = settingKey(request.childId());
		customSettingRedisTemplate.opsForValue().set(key, setting);
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
	public void initChatHistory(Long childId, Type type) {
		String key = historyKey(type, childId);
		if (Boolean.FALSE.equals(chatNodeRedisTemplate.hasKey(key))) {
			chatNodeRedisTemplate.opsForList().rightPushAll(key);
		}
	}

	@Transactional
	@Override
	public void appendChatHistory(
		final Long childId, final Type type, final AIMessage aiMessage, final MemberMessage memberMessage) {
		final String key = historyKey(type, childId);

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
	public void removeChatHistory(Long childId, Type type) {
		chatNodeRedisTemplate.delete(historyKey(type, childId));
	}

	@Override
	public List<ChatNode> loadChatHistory(Long childId, Type type) {
		return chatNodeRedisTemplate.opsForList().range(historyKey(type, childId), 0, -1);
	}

	private String settingKey(Long childId) {
		return SETTING_KEY_PREFIX + childId;
	}

	private String historyKey(Type type, Long childId) {
		return HISTORY_KEY_PREFIX + type.name().toLowerCase() + ":" + childId;
	}
}
