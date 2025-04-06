package com.aicheck.business.domain.voice_phishings.application.service;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.voice_phishings.entity.VoicePhishing;
import com.aicheck.business.domain.voice_phishings.presentation.dto.request.RegisterVoicePhishingRequest;
import com.aicheck.business.domain.voice_phishings.repository.VoicePhishingRepository;
import com.aicheck.business.global.error.BusinessErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoicePhishingService {

    private final VoicePhishingRepository voicePhishingRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void abc(Long memberId, RegisterVoicePhishingRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
        memberRepository.findAllByManagerIdAndDeletedAt()
        /*
        TODO: 가족들에게 알림 보내기
         */
        VoicePhishing voicePhishing = VoicePhishing.builder()
                .member(member)
                .manager(Member.withId(member.getManagerId()))
                .score(request.getScore())
                .phoneNumber(request.getPhoneNumber())
                .build();
        voicePhishingRepository.save(voicePhishing);
    }
}
