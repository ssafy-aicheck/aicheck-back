package com.aicheck.chatbot.infrastructure.client.business.dto.request;

import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.Result;

import lombok.Builder;

@Builder
public record SaveAllowanceRequest(
	Long childId,
	Long parentId,
	Integer amount,
	String firstCategoryName,
	String secondCategoryName,
	String description
) {

	public static SaveAllowanceRequest of(Long childId, Long parentId, Result result) {
		return SaveAllowanceRequest.builder()
			.childId(childId)
			.parentId(parentId)
			.amount(result.amount())
			.firstCategoryName(result.firstCategoryName())
			.secondCategoryName(result.secondCategoryName())
			.description(result.description())
			.build();
	}
}
