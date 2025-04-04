package com.aicheck.business.domain.allowance.entity;

import static com.aicheck.business.domain.allowance.entity.AllowanceRequest.Status.WAITING;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

import com.aicheck.business.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

	@Column(name = "parent_id", nullable = false)
	private Long parentId;

	@Column(name = "child_id", nullable = false)
	private Long childId;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "first_category_name", length = 30, nullable = false)
	private String firstCategoryName;

	@Column(name = "second_category_name", length = 30, nullable = false)
	private String secondCategoryName;

	@Enumerated(STRING)
	@Column(nullable = false)
	private Status status;

	@Column(name = "description")
	private String description;

	@Builder
	private AllowanceRequest(Long parentId, Long childId, Integer amount, String firstCategoryName,
		String secondCategoryName, String description) {
		this.parentId = parentId;
		this.childId = childId;
		this.amount = amount;
		this.firstCategoryName = firstCategoryName;
		this.secondCategoryName = secondCategoryName;
		this.status = WAITING;
		this.description = description;
	}

	public void updateStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		ACCEPTED, REJECTED, WAITING
	}
}