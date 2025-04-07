package com.aicheck.chatbot.application.service.redis;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.aicheck.chatbot.application.service.redis.dto.request.AIMessage;
import com.aicheck.chatbot.application.service.redis.dto.request.MemberMessage;
import com.aicheck.chatbot.domain.chat.ChatNode;
import com.aicheck.chatbot.domain.chat.ChatType;
import com.aicheck.chatbot.domain.chat.CustomSetting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisServiceImpl implements RedisService {
	private static final String SETTING_KEY_PREFIX = "chat:setting:";
	private static final String HISTORY_KEY_PREFIX = "chat:history:";

	private final RedisTemplate<String, CustomSetting> customSettingRedisTemplate;
	private final RedisTemplate<String, ChatNode> chatNodeRedisTemplate;

	@Override
	public void prepareChatSession(Long childId, ChatType chatType, CustomSetting setting) {
		try{
			customSettingRedisTemplate.opsForValue().set(settingKey(childId), setting);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void clearChatSession(Long childId, ChatType chatType) {
		customSettingRedisTemplate.delete(settingKey(childId));
		chatNodeRedisTemplate.delete(historyKey(chatType, childId));
	}

	@Override
	public void appendChatHistory(
		final Long childId, final ChatType chatType, final AIMessage aiMessage, final MemberMessage memberMessage) {
		final String key = historyKey(chatType, childId);
		chatNodeRedisTemplate.opsForList().rightPush(key, ChatNode.from(memberMessage));
		chatNodeRedisTemplate.opsForList().rightPush(key, ChatNode.from(aiMessage));
	}

	@Override
	public CustomSetting loadCustomSetting(final Long childId) {
		return customSettingRedisTemplate.opsForValue().get(settingKey(childId));
	}

	@Override
	public List<ChatNode> loadChatHistory(final Long childId, final ChatType chatType) {
		return chatNodeRedisTemplate.opsForList().range(historyKey(chatType, childId), 0, -1);
	}

	private String settingKey(final Long childId) {
		return SETTING_KEY_PREFIX + childId;
	}

	private String historyKey(final ChatType chatType, final Long childId) {
		return HISTORY_KEY_PREFIX + chatType.name() + ":" + childId;
	}
}