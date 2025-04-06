package com.aicheck.business.domain.allowance.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.global.entity.BaseEntity;

@Entity
@Table(name = "allowance_increase_requests")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class AllowanceIncreaseRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Member parent;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Member child;

    @Column(name = "before_amount", nullable = false)
    private Integer beforeAmount;

    @Column(name = "after_amount", nullable = false)
    private Integer afterAmount;

    @Column(name = "report_id", nullable = false)
    private String reportId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.WAITING;

    @Column(nullable = false, length = 255)
    private String summary;

    @Column(length = 255)
    private String description;

    @Builder
    private AllowanceIncreaseRequest(
        Member parent, Member child, Integer beforeAmount,
        Integer afterAmount, String reportId, String summary, String description) {
        this.parent = parent;
        this.child = child;
        this.beforeAmount = beforeAmount;
        this.afterAmount = afterAmount;
        this.reportId = reportId;
        this.summary = summary;
        this.description = description;
    }

    public void accept() {
        this.status = Status.ACCEPTED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }

    public boolean isAlreadyDecided() {
        return !this.status.equals(Status.WAITING);
    }

    public enum Status {
        ACCEPTED, REJECTED, WAITING
    }
}

