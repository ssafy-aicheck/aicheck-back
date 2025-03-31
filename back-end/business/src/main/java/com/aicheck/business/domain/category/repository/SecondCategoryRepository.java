package com.aicheck.business.domain.category.repository;

import com.aicheck.business.domain.category.entity.SecondCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondCategoryRepository extends JpaRepository<SecondCategory, Integer> {
    Optional<SecondCategory> findByIdAndFirstCategoryId(Integer secondId, Integer firstId);
}
