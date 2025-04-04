package com.aicheck.business.domain.allowance.infrastructure;

import static com.aicheck.business.domain.allowance.entity.QAllowanceIncreaseRequest.allowanceIncreaseRequest;
import static com.aicheck.business.domain.allowance.entity.QAllowanceRequest.allowanceRequest;
import static com.aicheck.business.domain.auth.domain.entity.QMember.member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.aicheck.business.domain.allowance.dto.AllowanceIncreaseRequestDto;
import com.aicheck.business.domain.allowance.dto.AllowanceRequestDto;
import com.aicheck.business.domain.allowance.dto.QAllowanceIncreaseRequestDto;
import com.aicheck.business.domain.allowance.dto.QAllowanceRequestDto;
import com.aicheck.business.domain.allowance.presentation.dto.response.AllowanceResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AllowanceQueryDslRepository {

	private final JPAQueryFactory queryFactory;

	public List<AllowanceResponse> findAllByParent(Long parentId) {
		return getResponses(parentIdEq(parentId));
	}

	public List<AllowanceResponse> findAllByChild(Long childId) {
		return getResponses(childIdEq(childId));
	}

	public Optional<AllowanceRequestDto> findById(Long id) {
		return Optional.ofNullable(queryFactory.select(
				new QAllowanceRequestDto(
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
			.join(allowanceRequest.child, member).fetchJoin()
			.where(IdEq(id), deletedIsNull())
			.orderBy(createdAtDesc())
			.fetchOne());
	}

	private List<AllowanceResponse> getResponses(BooleanExpression condition) {
		List<AllowanceRequestDto> allowanceList = queryFactory.select(
				new QAllowanceRequestDto(
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
			.join(allowanceRequest.child, member).fetchJoin()
			.where(condition, deletedIsNull())
			.fetch();

		List<AllowanceIncreaseRequestDto> allowanceIncreaseList = queryFactory.select(
				new QAllowanceIncreaseRequestDto(
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
			.join(allowanceIncreaseRequest.child, member).fetchJoin()
			.where(condition, deletedIsNull())
			.fetch();

		return Stream.concat(
				allowanceList.stream().map(AllowanceResponse::from),
				allowanceIncreaseList.stream().map(AllowanceResponse::from))
			.sorted()
			.toList();
	}

	private static BooleanExpression IdEq(Long id) {
		return id != null ? allowanceRequest.id.eq(id) : null;
	}

	private static BooleanExpression parentIdEq(Long id) {
		return id != null ? allowanceRequest.parent.id.eq(id) : null;
	}

	private static BooleanExpression childIdEq(Long id) {
		return id != null ? allowanceRequest.child.id.eq(id) : null;
	}

	private static BooleanExpression deletedIsNull() {
		return allowanceRequest.deletedAt.isNull();
	}

	private static OrderSpecifier<LocalDateTime> createdAtDesc() {
		return allowanceRequest.createdAt.desc();
	}
}