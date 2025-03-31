package com.aicheck.business.domain.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "first_categories")
@Getter
@NoArgsConstructor
public class FirstCategory {
    @Id
    private Integer id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @OneToMany(mappedBy = "firstCategory", fetch = FetchType.LAZY)
    private List<SecondCategory> secondCategories = new ArrayList<>();
}
