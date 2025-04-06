package com.aicheck.business.domain.voice_phishings.presentation;

import java.util.List;

import com.aicheck.business.domain.voice_phishings.application.service.PhishingService;
import com.aicheck.business.domain.voice_phishings.presentation.dto.request.RegisterVoicePhishingRequest;
import com.aicheck.business.domain.voice_phishings.application.service.VoicePhishingService;
import com.aicheck.business.domain.voice_phishings.presentation.dto.response.PhishingCountsResponse;
import com.aicheck.business.domain.voice_phishings.presentation.dto.response.PhishingResponse;
import com.aicheck.business.global.auth.annotation.CurrentMemberId;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phishings")
public class VoicePhishingController {

    private final VoicePhishingService voicePhishingService;
    private final PhishingService phishingService;

    @PostMapping
    public void registerVoicePhishing(@CurrentMemberId Long memberId,
                                      @RequestBody RegisterVoicePhishingRequest request) {
        voicePhishingService.abc(memberId, request);
    }

    @GetMapping
    public ResponseEntity<PhishingCountsResponse> getPhishingCounts(@CurrentMemberId Long memberId){
        return ResponseEntity.ok(phishingService.getPhishingCounts(memberId));
    }

    @GetMapping("/family")
    public ResponseEntity<List<PhishingResponse>> getPhishings(@CurrentMemberId Long memberId){
        return ResponseEntity.ok(phishingService.getPhishingList(memberId));
    }
}
