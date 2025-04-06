package com.aicheck.business.domain.voice_phishings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aicheck.business.domain.voice_phishings.entity.BadUrl;

public interface BadUrlRepository extends JpaRepository<BadUrl, Long> {
}
