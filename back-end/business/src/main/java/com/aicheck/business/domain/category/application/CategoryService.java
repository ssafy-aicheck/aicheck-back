package com.aicheck.business.domain.category.application;

import com.aicheck.business.domain.category.repository.FirstCategoryRepository;
import com.aicheck.business.domain.category.presentation.CategoryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final FirstCategoryRepository firstCategoryRepository;

    public List<CategoryResponse> getAllCategories() {
        return firstCategoryRepository.findAllWithSecondCategories().stream()
                .map(CategoryResponse::from)
                .toList();
    }
}

