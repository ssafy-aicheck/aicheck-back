package com.aicheck.business.domain.voice_phishings.application.service;

import static com.aicheck.business.global.infrastructure.event.Type.*;

import java.util.List;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.voice_phishings.entity.VoicePhishing;
import com.aicheck.business.domain.voice_phishings.presentation.dto.request.RegisterVoicePhishingRequest;
import com.aicheck.business.domain.voice_phishings.repository.VoicePhishingRepository;
import com.aicheck.business.global.error.BusinessErrorCodes;
import com.aicheck.business.global.infrastructure.event.AlarmEventProducer;
import com.aicheck.business.global.infrastructure.event.Type;
import com.aicheck.business.global.infrastructure.event.dto.request.AlarmEventMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoicePhishingService {

	private final VoicePhishingRepository voicePhishingRepository;
	private final MemberRepository memberRepository;
	private final AlarmEventProducer alarmEventProducer;

	@Transactional
	public void abc(final Long memberId, final RegisterVoicePhishingRequest request) {
		final Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));

		final VoicePhishing voicePhishing = VoicePhishing.builder()
			.member(member)
			.manager(Member.withId(member.getManagerId()))
			.score(request.getScore())
			.phoneNumber(request.getPhoneNumber())
			.build();
		voicePhishingRepository.save(voicePhishing);

		memberRepository.findMemberIdsByManagerId(memberId).forEach(id -> alarmEventProducer.sendEvent(
			AlarmEventMessage.of(
				id,
				getTitle(member.getName()),
				getBody(member.getName(), request.getPhoneNumber(), request.getScore()),
				VOICE,
				voicePhishing.getId()
			)
		));
	}

	private String getTitle(final String name) {
		return String.format("%s님에게 보이스피싱 위험이 감지됐습니다.", name);
	}

	private String getBody(final String name, final String phoneNumber, final Float score) {
		return String.format("%s님에게 %s 번호로 걸려온 전화가 보이스피싱 가능성이 %.1f점으로 감지되었습니다.", name, phoneNumber, score);
	}
}
