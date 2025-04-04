package com.aicheck.business.domain.allowance.presentation.dto.response;

import java.time.LocalDateTime;

import com.aicheck.business.domain.allowance.dto.AllowanceIncreaseRequestDto;
import com.aicheck.business.domain.allowance.dto.AllowanceRequestDto;

import lombok.Builder;

@Builder
public record AllowanceResponse(
	 Long id,
	 String type,
	 String status,
	 String childName,
	 Integer amount,
	 String description,
	 LocalDateTime createdAt
) implements Comparable<AllowanceResponse> {

	public static AllowanceResponse from (AllowanceRequestDto allowanceRequestDto) {
		return AllowanceResponse.builder()
			.id(allowanceRequestDto.id())
			.type("ONE_TIME")
			.status(allowanceRequestDto.status().name())
			.childName(allowanceRequestDto.childName())
			.amount(allowanceRequestDto.amount())
			.description(allowanceRequestDto.description())
			.createdAt(allowanceRequestDto.createdAt())
			.build();
	}

	public static AllowanceResponse from (AllowanceIncreaseRequestDto allowanceIncreaseRequestDto) {
		return AllowanceResponse.builder()
			.id(allowanceIncreaseRequestDto.id())
			.type("INCREASE")
			.status(allowanceIncreaseRequestDto.status().name())
			.childName(allowanceIncreaseRequestDto.childName())
			.amount(allowanceIncreaseRequestDto.afterAmount() - allowanceIncreaseRequestDto.beforeAmount())
			.description(allowanceIncreaseRequestDto.description())
			.createdAt(allowanceIncreaseRequestDto.createdAt())
			.build();
	}

	@Override
	public int compareTo(AllowanceResponse o) {
		return o.createdAt().compareTo(this.createdAt());
	}
}
