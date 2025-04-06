package com.aicheck.business.domain.voice_phishings.presentation.dto.request;

import lombok.Getter;

@Getter
public class RegisterVoicePhishingRequest {
    private String phoneNumber;
    private Float score;
}
