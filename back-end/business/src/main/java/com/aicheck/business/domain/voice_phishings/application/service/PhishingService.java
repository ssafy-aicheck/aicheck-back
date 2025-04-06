package com.aicheck.business.domain.voice_phishings.application.service;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.voice_phishings.application.dto.PhishingSimpleDto;
import com.aicheck.business.domain.voice_phishings.infrastructure.PhishingQueryDslRepository;
import com.aicheck.business.domain.voice_phishings.presentation.dto.response.PhishingCountsResponse;
import com.aicheck.business.domain.voice_phishings.presentation.dto.response.PhishingResponse;
import com.aicheck.business.global.error.BusinessErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PhishingService {

	private final MemberRepository memberRepository;
	private final PhishingQueryDslRepository phishingQueryDslRepository;

	public PhishingCountsResponse getPhishingCounts(final Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));

		Long managerId = member.getManagerId();
		LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();

		List<PhishingSimpleDto> allPhishings = phishingQueryDslRepository.findAllPhishingsSince(startOfMonth);

		Map<Long, List<PhishingSimpleDto>> byManager = allPhishings.stream()
			.collect(Collectors.groupingBy(PhishingSimpleDto::managerId));

		int voiceCount = (int) allPhishings.stream()
			.filter(dto -> dto.memberId().equals(memberId) && dto.type().name().equals("VOICE"))
			.count();

		int urlCount = (int) allPhishings.stream()
			.filter(dto -> dto.memberId().equals(memberId) && dto.type().name().equals("URL"))
			.count();

		int familyCount = byManager.getOrDefault(managerId, List.of()).size();

		double totalCountAverage = byManager.entrySet().stream()
			.filter(entry -> !entry.getKey().equals(managerId))
			.mapToInt(entry -> entry.getValue().size())
			.average()
			.orElse(0.0);

		return PhishingCountsResponse.of((float) totalCountAverage, urlCount, voiceCount, familyCount);
	}

	public List<PhishingResponse> getPhishingList(final Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));

		Long managerId = member.getManagerId();
		List<Long> familyIds = memberRepository.findMemberIdsByManagerId(managerId);
		familyIds.add(managerId);

		LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
		LocalDateTime now = LocalDateTime.now();

		List<PhishingResponse> result = phishingQueryDslRepository.findFamilyPhishingLogs(familyIds, startOfMonth, now);
		Collections.sort(result);
		return result;
	}
}