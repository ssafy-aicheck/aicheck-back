package com.aicheck.alarm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "fcm_tokens")
@Entity
public class FCMToken {

	@Id
	@Column(name = "member_id")
	private Long memberId;

	@Column(length = 255, nullable = false)
	private String token;

	@Builder
	private FCMToken(Long memberId, String token) {
		this.memberId = memberId;
		this.token = token;
	}

	public void changeToken(String token) {
		this.token = token;
	}
}
