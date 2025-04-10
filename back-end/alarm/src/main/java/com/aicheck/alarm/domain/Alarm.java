package com.aicheck.alarm.domain;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.aicheck.alarm.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "alarms")
@Entity
public class Alarm extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "title", length = 80, nullable = false)
	private String title;

	@Column(name = "body", length = 1000, nullable = false)
	private String body;

	@Column(name = "is_read", nullable = false)
	private boolean isRead;

	@Enumerated(STRING)
	@Column(name = "type", nullable = false)
	private Type type;

	@Column(name = "end_point_id")
	private Long endPointId;

	@Builder
	private Alarm(Long memberId, String title, String body, Type type, Long endPointId) {
		this.memberId = memberId;
		this.title = title;
		this.body = body;
		this.isRead = false;
		this.type = type;
		this.endPointId = endPointId;
	}

	public void changeRead(boolean read) {
		this.isRead = read;
	}
}
