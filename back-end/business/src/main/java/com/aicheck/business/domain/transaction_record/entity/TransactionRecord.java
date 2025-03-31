package com.aicheck.business.domain.transaction_record.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "transaction_records")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "first_category_name", length = 30, nullable = false)
    private String firstCategoryName;

    @Column(name = "second_category_name", length = 30)
    private String secondCategoryName;

    @Column(name = "is_dutch_pay", nullable = false)
    private Boolean isDutchPay;

    @Column(name = "display_name", length = 20, nullable = false)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TransactionType type;

    @Column(nullable = false)
    private Integer amount;

    @Column(length = 60)
    private String description;

    @Column
    private Integer rating;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
