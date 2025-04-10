package com.aicheck.bank.domain.member.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountCheckRequest(
	@NotNull(message = "accountNo가 없습니다.")
	@Size(min = 12, max = 12, message = "12자리가 아닙니다.")
	String accountNo
) {
}
