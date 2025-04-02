package com.aicheck.chatbot.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aicheck.chatbot.domain.Prompt;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
}
