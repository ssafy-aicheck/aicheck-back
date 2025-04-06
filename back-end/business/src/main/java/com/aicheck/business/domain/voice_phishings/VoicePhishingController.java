package com.aicheck.business.domain.voice_phishings;

import com.aicheck.business.global.auth.annotation.CurrentMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phishings")
public class VoicePhishingController {

    private final VoicePhishingService voicePhishingService;

    @PostMapping
    public void registerVoicePhishing(@CurrentMemberId Long memberId,
                                      @RequestBody RegisterVoicePhishingRequest request) {
        voicePhishingService.abc(memberId, request);
    }


}
