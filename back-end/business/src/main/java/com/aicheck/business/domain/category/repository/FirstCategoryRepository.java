package com.aicheck.business.domain.category.repository;

import com.aicheck.business.domain.category.entity.FirstCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FirstCategoryRepository extends JpaRepository<FirstCategory, Integer> {
    @Query("SELECT fc FROM FirstCategory fc LEFT JOIN FETCH fc.secondCategories")
    List<FirstCategory> findAllWithSecondCategories();
}