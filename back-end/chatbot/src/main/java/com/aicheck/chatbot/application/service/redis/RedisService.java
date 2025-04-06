package com.aicheck.chatbot.application.service.redis;

import java.util.List;

import com.aicheck.chatbot.application.service.redis.dto.request.AIMessage;
import com.aicheck.chatbot.application.service.redis.dto.request.CustomSettingRequest;
import com.aicheck.chatbot.application.service.redis.dto.request.MemberMessage;
import com.aicheck.chatbot.domain.chat.ChatNode;
import com.aicheck.chatbot.domain.chat.CustomSetting;
import com.aicheck.chatbot.domain.chat.ChatType;

public interface RedisService {
	void prepareChatSession(Long childId, ChatType chatType, CustomSetting setting);
	void clearChatSession(Long childId, ChatType chatType);
	void appendChatHistory(Long childId, ChatType chatType, AIMessage aiMessage, MemberMessage memberMessage);
	CustomSetting loadCustomSetting(Long childId);
	List<ChatNode> loadChatHistory(Long childId, ChatType chatType);
}
