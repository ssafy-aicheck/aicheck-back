package com.aicheck.chatbot.presentation.prompt.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record SavePromptRequest(
	@NotNull(message = "childId가 없습니다.")
	Long childId,
	@NotNull(message = "managerId가 없습니다.")
	Long managerId,
	@NotNull(message = "birth가 없습니다.")
	LocalDate birth,
	@NotNull(message = "gender가 없습니다.")
	String gender
) {
}
