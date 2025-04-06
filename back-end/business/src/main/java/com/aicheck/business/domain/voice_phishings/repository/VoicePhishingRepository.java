package com.aicheck.business.domain.voice_phishings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aicheck.business.domain.voice_phishings.entity.VoicePhishing;

public interface VoicePhishingRepository extends JpaRepository<VoicePhishing, Long> {
}
