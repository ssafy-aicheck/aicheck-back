package com.aicheck.alarm.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aicheck.alarm.domain.FCMToken;

public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
	Optional<FCMToken> findByMemberId(Long memberId);
	void deleteByMemberId(Long memberId);
}
