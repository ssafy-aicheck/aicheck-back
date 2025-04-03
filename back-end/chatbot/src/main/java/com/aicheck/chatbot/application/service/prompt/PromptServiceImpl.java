package com.aicheck.chatbot.application.service.prompt;

import static com.aicheck.chatbot.common.error.ChatbotErrorCodes.NOT_FOUND_PROMPT;
import static com.aicheck.chatbot.common.error.ChatbotErrorCodes.UNAUTHORIZED_CHANGE_PROMPT;
import static com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.chatbot.application.service.prompt.dto.response.PromptInfo;
import com.aicheck.chatbot.common.exception.ChatbotException;
import com.aicheck.chatbot.domain.Prompt;
import com.aicheck.chatbot.domain.categoryDifficulty.CategoryDifficulty;
import com.aicheck.chatbot.domain.repository.PromptRepository;
import com.aicheck.chatbot.presentation.prompt.dto.request.SavePromptRequest;
import com.aicheck.chatbot.presentation.prompt.dto.request.UpdatePromptRequest;
import com.aicheck.chatbot.util.JsonMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PromptServiceImpl implements PromptService {

	private final JsonMapper jsonMapper;
	private final PromptRepository promptRepository;

	@Transactional
	@Override
	public void savePrompt(final SavePromptRequest request) {
		final List<CategoryDifficulty> sampleCategoryDifficulties = createSampleCategoryDifficulties();
		final String categoryDifficulty = jsonMapper.toJson(sampleCategoryDifficulties);

		promptRepository.save(Prompt.builder()
			.childId(request.childId())
			.managerId(request.managerId())
			.birth(request.birth())
			.categoryDifficulty(categoryDifficulty)
			.gender(request.gender())
			.build()
		);
	}

	@Override
	public PromptInfo getPrompt(final Long childId) {
		final Prompt prompt = promptRepository.findById(childId)
			.orElseThrow(() -> new ChatbotException(NOT_FOUND_PROMPT));

		final List<CategoryDifficulty> categoryDifficulties = jsonMapper.fromJsonList(
			prompt.getCategoryDifficulty(),
			CategoryDifficulty.class
		);

		return PromptInfo.of(prompt, categoryDifficulties);
	}

	@Transactional
	@Override
	public PromptInfo updatePrompt(final Long managerId, final UpdatePromptRequest request) {
		final Prompt prompt = promptRepository.findById(request.childId())
			.orElseThrow(() -> new ChatbotException(NOT_FOUND_PROMPT));

		if (!prompt.getManagerId().equals(managerId)) {
			throw new ChatbotException(UNAUTHORIZED_CHANGE_PROMPT);
		}

		final String categoryDifficulty = jsonMapper.toJson(request.categoryDifficulties());

		prompt.updatePrompt(categoryDifficulty, request.content());
		return PromptInfo.of(prompt, request.categoryDifficulties());
	}
}
