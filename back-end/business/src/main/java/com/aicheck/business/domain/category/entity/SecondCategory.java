package com.aicheck.business.domain.category.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "second_categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SecondCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(name = "display_name", length = 30)
    private String displayName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_category_id", nullable = false)
    private FirstCategory firstCategory;
}
