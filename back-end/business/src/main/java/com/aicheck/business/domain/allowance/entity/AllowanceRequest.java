package com.aicheck.business.domain.allowance.entity;

import static com.aicheck.business.domain.allowance.entity.AllowanceRequest.Status.*;
import static com.aicheck.business.domain.allowance.entity.AllowanceRequest.Status.WAITING;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "allowance_requests")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class AllowanceRequest extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "parent_id")
	private Member parent;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "child_id")
	private Member child;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "first_category_name", length = 30, nullable = false)
	private String firstCategoryName;

	@Column(name = "second_category_name", length = 30, nullable = false)
	private String secondCategoryName;

	@Enumerated(STRING)
	@Column(nullable = false)
	private Status status = WAITING;

	@Column(name = "description")
	private String description;

	@Builder
	private AllowanceRequest(Member parent, Member child, Integer amount, String firstCategoryName,
		String secondCategoryName, String description) {
		this.parent = parent;
		this.child = child;
		this.amount = amount;
		this.firstCategoryName = firstCategoryName;
		this.secondCategoryName = secondCategoryName;
		this.description = description;
	}

	public void accept() {
		this.status = ACCEPTED;
	}

	public void reject() {
		this.status = REJECTED;
	}

	public boolean isAlreadyDecided() {
		return !this.status.equals(WAITING);
	}

	public enum Status {
		ACCEPTED, REJECTED, WAITING
	}
}