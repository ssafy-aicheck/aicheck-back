package com.aicheck.chatbot.domain;

import static lombok.AccessLevel.PROTECTED;

import java.util.Date;

import com.aicheck.chatbot.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "prompts")
@Entity
public class Prompt extends BaseEntity {

	@Id
	@Column(name = "child_id")
	private Long childId;

	@Column(name = "manager_id", nullable = false)
	private Long managerId;

	@Column(name = "birth", nullable = false)
	private Date birth;

	@Column(name = "category_difficulty", nullable = false, columnDefinition = "TEXT")
	private String categoryDifficulty;

	@Column(name = "content", nullable = false)
	private String content;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Builder
	private Prompt(Long childId, Long managerId, Date birth, String categoryDifficulty, String content, Gender gender) {
		this.childId = childId;
		this.managerId = managerId;
		this.birth = birth;
		this.categoryDifficulty = categoryDifficulty;
		this.content = content;
		this.gender = gender;
	}

	public void updateCategoryDifficulty(String categoryDifficulty) {
		this.categoryDifficulty = categoryDifficulty;
	}
}
