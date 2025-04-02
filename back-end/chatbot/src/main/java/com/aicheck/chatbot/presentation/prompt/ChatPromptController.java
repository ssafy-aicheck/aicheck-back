package com.aicheck.chatbot.presentation.prompt;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.chatbot.application.service.PromptService;
import com.aicheck.chatbot.common.resolver.CurrentMemberId;
import com.aicheck.chatbot.presentation.prompt.dto.request.SavePromptRequest;
import com.aicheck.chatbot.presentation.prompt.dto.request.UpdatePromptRequest;
import com.aicheck.chatbot.presentation.prompt.dto.response.PromptResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/prompts")
@RestController
public class ChatPromptController {

	private final PromptService promptService;

	@PostMapping("/")
	public ResponseEntity<Void> savePrompt(@Valid @RequestBody SavePromptRequest request) {
		promptService.savePrompt(request);
		return ResponseEntity.status(CREATED).build();
	}

	@GetMapping("/{childId}")
	public ResponseEntity<PromptResponse> findPrompt(@PathVariable(name = "childId") @NotNull Long childId) {
		return ResponseEntity.ok(promptService.searchPrompt(childId));
	}

	@PatchMapping("/")
	public ResponseEntity<PromptResponse> updatePrompt(
		@CurrentMemberId Long managerId,
		@Valid @RequestBody UpdatePromptRequest request) {
		return ResponseEntity.ok(promptService.updatePrompt(managerId, request));
	}
}
