package com.aicheck.business.domain.voice_phishings.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.voice_phishings.presentation.dto.response.PhishingCountsResponse;
import com.aicheck.business.domain.voice_phishings.presentation.dto.response.PhishingResponse;
import com.aicheck.business.domain.voice_phishings.repository.BadUrlRepository;
import com.aicheck.business.domain.voice_phishings.repository.VoicePhishingRepository;
import com.aicheck.business.global.error.BusinessErrorCodes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PhishingService {

	private final MemberRepository memberRepository;
	private final VoicePhishingRepository voicePhishingRepository;
	private final BadUrlRepository badUrlRepository;

	public PhishingCountsResponse getPhishingCounts(final Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));


		return null;
	}

	public List<PhishingResponse> getPhishingList(Long memberId) {
		return null;
	}
}
