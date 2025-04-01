package com.aicheck.alarm.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aicheck.alarm.domain.FCMToken;

public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
	Optional<FCMToken> findByMemberIdAndDeletedAtIsNull(Long memberId);
}
