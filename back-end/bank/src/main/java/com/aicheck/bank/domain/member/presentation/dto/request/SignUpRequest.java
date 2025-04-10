package com.aicheck.bank.domain.member.presentation.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
	@NotNull(message = "email이 없습니다.")
	String email,
	@NotNull(message = "name이 없습니다.")
	@Size(min = 3, max = 3, message = "이름은 3자만 가능합니다.")
	String name,
	@NotNull(message = "brith가 없습니다.")
	LocalDate brith,
	@NotNull(message = "accountNo가 없습니다.")
	@Size(min = 12, max = 12, message = "12자리 숫자만 가능합니다.")
	String accountNo
) {
}
