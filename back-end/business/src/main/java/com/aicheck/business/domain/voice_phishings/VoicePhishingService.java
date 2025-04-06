package com.aicheck.business.domain.voice_phishings;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
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
        /*
        TODO: 가족들에게 알림 보내기
         */
        VoicePhishing voicePhishing = VoicePhishing.builder()
                .memberId(member.getId())
                .managerId(member.getManagerId())
                .score(request.getScore())
                .phoneNumber(request.getPhoneNumber())
                .build();
        voicePhishingRepository.save(voicePhishing);
    }

}
