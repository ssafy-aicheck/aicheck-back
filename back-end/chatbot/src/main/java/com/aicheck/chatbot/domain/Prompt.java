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

	@Column(name = "content", nullable = false)
	private String content;

	@Enumerated(EnumType.STRING)
	private Gender gender;
}
