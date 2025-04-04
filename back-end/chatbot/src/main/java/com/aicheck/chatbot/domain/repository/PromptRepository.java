package com.aicheck.chatbot.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aicheck.chatbot.domain.Prompt;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
	Optional<Prompt> findByChildIdAndDeletedAtIsNull(Long childId);
}
