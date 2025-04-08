package com.aicheck.chatbot.presentation.chatbot;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.chatbot.application.service.chatbot.ChatbotService;
import com.aicheck.chatbot.common.resolver.CurrentMemberId;
import com.aicheck.chatbot.domain.chat.ChatType;
import com.aicheck.chatbot.presentation.chatbot.dto.request.EndChatRequest;
import com.aicheck.chatbot.presentation.chatbot.dto.request.SendChatRequest;
import com.aicheck.chatbot.presentation.chatbot.dto.request.StartChatRequest;
import com.aicheck.chatbot.presentation.chatbot.dto.response.PersuadeChatResponse;
import com.aicheck.chatbot.presentation.chatbot.dto.response.QuestionChatResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class ChatbotController {

	private final ChatbotService chatbotService;

	@PostMapping("/start")
	public ResponseEntity<Void> startChat(
		@CurrentMemberId final Long memberId,
		@Valid @RequestBody final StartChatRequest request) {
		chatbotService.startChat(memberId, request.chatType());
		return ResponseEntity.status(CREATED).build();
	}

	@PostMapping("/persuade")
	public ResponseEntity<PersuadeChatResponse> sendPersuadeChat(
		@CurrentMemberId final Long childId,
		@Valid @RequestBody final SendChatRequest request) {
		return ResponseEntity.status(CREATED).body(chatbotService.sendPersuadeChat(childId, request.message()));
	}

	@PostMapping("/question")
	public ResponseEntity<QuestionChatResponse> sendQuestionChat(
		@CurrentMemberId final Long childId,
		@Valid @RequestBody final SendChatRequest request) {
		return ResponseEntity.status(CREATED).body(chatbotService.sendQuestionChat(childId, request.message()));
	}

	@DeleteMapping("/end")
	public ResponseEntity<Void> endChat(
		@CurrentMemberId final Long childId,
		@RequestParam(name = "chat-type") final ChatType chatType) {
		chatbotService.endChat(childId, chatType);
		return ResponseEntity.noContent().build();
	}
}
