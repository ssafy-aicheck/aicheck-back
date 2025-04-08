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

	public static SaveAllowanceRequest from(final Long childId, final Long parentId, final Result result) {
		return SaveAllowanceRequest.builder()
			.childId(childId)
			.parentId(parentId)
			.amount(result.amount())
			.firstCategoryName(result.firstCategoryName() == null ? "기타" : result.firstCategoryName())
			.secondCategoryName(result.secondCategoryName() == null ? "기타" : result.secondCategoryName())
			.description(result.description())
			.build();
	}
}
