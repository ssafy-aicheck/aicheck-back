package com.aicheck.business.domain.allowance.infrastructure;

import static com.aicheck.business.domain.allowance.entity.QAllowanceIncreaseRequest.allowanceIncreaseRequest;
import static com.aicheck.business.domain.allowance.entity.QAllowanceRequest.allowanceRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.aicheck.business.domain.allowance.dto.AllowanceIncreaseRequestDto;
import com.aicheck.business.domain.allowance.dto.AllowanceRequestDto;
import com.aicheck.business.domain.allowance.dto.QAllowanceIncreaseRequestDto;
import com.aicheck.business.domain.allowance.dto.QAllowanceRequestDto;
import com.aicheck.business.domain.allowance.presentation.dto.response.AllowanceResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AllowanceQueryDslRepository {

	private final JPAQueryFactory queryFactory;

	public List<AllowanceResponse> findAllByParent(Long parentId) {
		return getResponsesByParent(parentId);
	}

	public List<AllowanceResponse> findAllByChild(Long childId) {
		return getResponsesByChild(childId);
	}

	public Optional<AllowanceRequestDto> findById(Long id) {
		return Optional.ofNullable(
			queryFactory
				.select(new QAllowanceRequestDto(
					allowanceRequest.id,
					allowanceRequest.parent.id,
					allowanceRequest.child.id,
					allowanceRequest.child.name,
					allowanceRequest.child.profileUrl,
					allowanceRequest.amount,
					allowanceRequest.firstCategoryName,
					allowanceRequest.secondCategoryName,
					allowanceRequest.status,
					allowanceRequest.description,
					allowanceRequest.createdAt
				))
				.from(allowanceRequest)
				.where(allowanceRequest.id.eq(id), allowanceRequest.deletedAt.isNull())
				.fetchOne()
		);
	}

	private List<AllowanceResponse> getResponsesByParent(Long parentId) {
		List<AllowanceRequestDto> requests = getAllowanceRequests(allowanceRequest.parent.id.eq(parentId));
		List<AllowanceIncreaseRequestDto> increases = getAllowanceIncreases(allowanceIncreaseRequest.parent.id.eq(parentId));

		return mergeResponses(requests, increases);
	}

	private List<AllowanceResponse> getResponsesByChild(Long childId) {
		List<AllowanceRequestDto> requests = getAllowanceRequests(allowanceRequest.child.id.eq(childId));
		List<AllowanceIncreaseRequestDto> increases = getAllowanceIncreases(allowanceIncreaseRequest.child.id.eq(childId));

		return mergeResponses(requests, increases);
	}

	private List<AllowanceRequestDto> getAllowanceRequests(BooleanExpression condition) {
		return queryFactory
			.select(new QAllowanceRequestDto(
				allowanceRequest.id,
				allowanceRequest.parent.id,
				allowanceRequest.child.id,
				allowanceRequest.child.name,
				allowanceRequest.child.profileUrl,
				allowanceRequest.amount,
				allowanceRequest.firstCategoryName,
				allowanceRequest.secondCategoryName,
				allowanceRequest.status,
				allowanceRequest.description,
				allowanceRequest.createdAt
			))
			.from(allowanceRequest)
			.where(condition, allowanceRequest.deletedAt.isNull())
			.fetch();
	}

	private List<AllowanceIncreaseRequestDto> getAllowanceIncreases(BooleanExpression condition) {
		return queryFactory
			.select(new QAllowanceIncreaseRequestDto(
				allowanceIncreaseRequest.id,
				allowanceIncreaseRequest.parent.id,
				allowanceIncreaseRequest.child.id,
				allowanceIncreaseRequest.child.name,
				allowanceIncreaseRequest.child.profileUrl,
				allowanceIncreaseRequest.beforeAmount,
				allowanceIncreaseRequest.afterAmount,
				allowanceIncreaseRequest.reportId,
				allowanceIncreaseRequest.status,
				allowanceIncreaseRequest.summary,
				allowanceIncreaseRequest.description,
				allowanceIncreaseRequest.createdAt
			))
			.from(allowanceIncreaseRequest)
			.where(condition, allowanceIncreaseRequest.deletedAt.isNull())
			.fetch();
	}

	private List<AllowanceResponse> mergeResponses(List<AllowanceRequestDto> requests, List<AllowanceIncreaseRequestDto> increases) {
		return Stream.concat(
				requests.stream().map(AllowanceResponse::from),
				increases.stream().map(AllowanceResponse::from)
			)
			.sorted()
			.toList();
	}
}