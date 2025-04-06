package com.aicheck.business.domain.voice_phishings;

import lombok.Getter;

@Getter
public class RegisterVoicePhishingRequest {
    private String phoneNumber;
    private Float score;
}
