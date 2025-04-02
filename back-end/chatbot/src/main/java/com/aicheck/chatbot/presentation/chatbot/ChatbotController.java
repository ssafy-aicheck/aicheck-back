package com.aicheck.chatbot.presentation.chatbot;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.chatbot.application.service.ChatbotService;
import com.aicheck.chatbot.application.service.RedisService;
import com.aicheck.chatbot.common.resolver.CurrentMemberId;
import com.aicheck.chatbot.presentation.chatbot.dto.request.EndChatRequest;
import com.aicheck.chatbot.presentation.chatbot.dto.request.SendChatRequest;
import com.aicheck.chatbot.presentation.chatbot.dto.request.StartChatRequest;
import com.aicheck.chatbot.presentation.chatbot.dto.response.ChatResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class ChatbotController {

	private final ChatbotService chatbotService;
	private final RedisService redisService;

	@PostMapping("/start")
	public ResponseEntity<Void> startChat(@Valid @RequestBody StartChatRequest request) {
		// chatbotService.startChat();
		return ResponseEntity.status(CREATED).build();
	}

	@PostMapping("/send")
	public ResponseEntity<ChatResponse> sendChat(
		@CurrentMemberId Long childId,
		@Valid @RequestBody SendChatRequest request) {
		// chatbotService.sendChat();
		return ResponseEntity.status(CREATED).build();
	}

	@DeleteMapping("/end")
	public ResponseEntity<Void> endChat(@Valid @RequestBody EndChatRequest request) {
		// redisService.endChat();
		return ResponseEntity.noContent().build();
	}
}
