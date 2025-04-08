package com.aicheck.alarm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aicheck.alarm.domain.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
	List<Alarm> findAllByMemberIdAndDeletedAtIsNullOrderByCreatedAtDesc(Long memberId);
	Optional<Alarm> findByIdAndMemberIdAndDeletedAtIsNull(Long id, Long memberId);
}
