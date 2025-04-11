package com.aicheck.business.domain.voice_phishings.application.service;

import static com.aicheck.business.global.infrastructure.event.Type.VOICE;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.voice_phishings.entity.BadUrl;
import com.aicheck.business.domain.voice_phishings.presentation.dto.request.SaveBadUrlRequest;
import com.aicheck.business.domain.voice_phishings.repository.BadUrlRepository;
import com.aicheck.business.global.error.BusinessErrorCodes;
import com.aicheck.business.global.infrastructure.event.AlarmEventProducer;
import com.aicheck.business.global.infrastructure.event.dto.request.AlarmEventMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BadUrlService {

	private final BadUrlRepository badUrlRepository;
	private final MemberRepository memberRepository;
	private final AlarmEventProducer alarmEventProducer;

	@Transactional
	public void saveBadUrl(final Long memberId, final SaveBadUrlRequest request) {
		final Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));

		final BadUrl badUrl = BadUrl.builder()
			.member(member)
			.manager(Member.withId(member.getManagerId()))
			.url(request.url())
			.score(request.score())
			.build();
		badUrlRepository.save(badUrl);

		memberRepository.findMemberIdsByManagerId(memberId).forEach(id -> alarmEventProducer.sendEvent(
			AlarmEventMessage.of(
				id,
				getTitle(member.getName()),
				getBody(member.getName(), request.score()),
				VOICE,
				badUrl.getId()
			)
		));
	}

	private String getTitle(final String name) {
		return String.format("%s님에게 스미싱 위험이 감지됐습니다.", name);
	}

	private String getBody(final String name, final Float score) {
		return String.format("%s님이 수신한 문자에 포함된 URL이 스미싱 위험 점수 %d점으로 감지되었습니다.", name, Math.round(score * 100));
	}

	// private String getBody(final String name, final String url, final Float score) {
	// 	return String.format("%s님이 수신한 문자에 포함된 URL [%s]이 스미싱 위험 점수 %d점으로 감지되었습니다.", name, url, Math.round(score * 100));
	// }
}
