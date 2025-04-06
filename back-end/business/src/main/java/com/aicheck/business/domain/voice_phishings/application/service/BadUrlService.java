package com.aicheck.business.domain.voice_phishings.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.voice_phishings.entity.BadUrl;
import com.aicheck.business.domain.voice_phishings.presentation.dto.request.SaveBadUrlRequest;
import com.aicheck.business.domain.voice_phishings.repository.BadUrlRepository;
import com.aicheck.business.global.error.BusinessErrorCodes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BadUrlService {

	private final BadUrlRepository badUrlRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void saveBadUrl(final Long memberId, final SaveBadUrlRequest request) {
		final Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));

		// TODO 가족들한테 알림 보내기

		badUrlRepository.save(BadUrl.builder()
			.member(member)
			.manager(Member.withId(member.getManagerId()))
			.url(request.url())
			.score(request.score())
			.build());
	}
}
