package com.aicheck.business.domain.category.repository;

import com.aicheck.business.domain.category.entity.FirstCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FirstCategoryRepository extends JpaRepository<FirstCategory, Long> {
    @Query("SELECT DISTINCT f FROM FirstCategory f LEFT JOIN FETCH f.secondCategories")
    List<FirstCategory> findAllWithSecondCategories();
}

