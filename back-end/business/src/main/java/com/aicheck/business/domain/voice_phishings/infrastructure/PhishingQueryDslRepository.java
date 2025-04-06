package com.aicheck.business.domain.voice_phishings.infrastructure;

import com.aicheck.business.domain.voice_phishings.application.dto.PhishingSimpleDto;
import com.aicheck.business.domain.voice_phishings.application.dto.QPhishingSimpleDto;
import com.aicheck.business.domain.voice_phishings.entity.PhishingType;
import com.aicheck.business.domain.voice_phishings.presentation.dto.response.QPhishingResponse;
import com.aicheck.business.domain.voice_phishings.presentation.dto.response.PhishingResponse;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.aicheck.business.domain.voice_phishings.entity.PhishingType.*;
import static com.aicheck.business.domain.voice_phishings.entity.PhishingType.VOICE;
import static com.aicheck.business.domain.voice_phishings.entity.PhishingType.URL;
import static com.aicheck.business.domain.voice_phishings.entity.QVoicePhishing.voicePhishing;
import static com.aicheck.business.domain.voice_phishings.entity.QBadUrl.badUrl;
import static com.querydsl.core.types.dsl.Expressions.*;

@RequiredArgsConstructor
@Repository
public class PhishingQueryDslRepository {

	private final JPAQueryFactory queryFactory;

	public List<PhishingSimpleDto> findAllPhishingsSince(LocalDateTime from) {
		List<PhishingSimpleDto> voiceList = queryFactory
			.select(new QPhishingSimpleDto(
				voicePhishing.member.id,
				voicePhishing.manager.id,
				constant(VOICE),
				voicePhishing.createdAt))
			.from(voicePhishing)
			.where(voicePhishing.createdAt.goe(from))
			.fetch();

		List<PhishingSimpleDto> urlList = queryFactory
			.select(new QPhishingSimpleDto(
				badUrl.member.id,
				badUrl.manager.id,
				constant(URL),
				badUrl.createdAt))
			.from(badUrl)
			.where(badUrl.createdAt.goe(from))
			.fetch();

		return Stream.concat(voiceList.stream(), urlList.stream()).toList();
	}

	public List<PhishingResponse> findFamilyPhishingLogs(List<Long> memberIds, LocalDateTime from, LocalDateTime to) {
		List<PhishingResponse> voiceLogs = queryFactory
			.select(new QPhishingResponse(
				voicePhishing.id,
				voicePhishing.member.name,
				constant(VOICE),
				null,
				voicePhishing.phoneNumber,
				voicePhishing.score,
				voicePhishing.createdAt))
			.from(voicePhishing)
			.where(
				voicePhishing.member.id.in(memberIds),
				voicePhishing.createdAt.between(from, to))
			.fetch();

		List<PhishingResponse> urlLogs = queryFactory
			.select(new QPhishingResponse(
				badUrl.id,
				badUrl.member.name,
				constant(URL),
				badUrl.url,
				null,
				badUrl.score,
				badUrl.createdAt))
			.from(badUrl)
			.where(
				badUrl.member.id.in(memberIds),
				badUrl.createdAt.between(from, to))
			.fetch();

		return Stream.concat(voiceLogs.stream(), urlLogs.stream()).toList();
	}
}
