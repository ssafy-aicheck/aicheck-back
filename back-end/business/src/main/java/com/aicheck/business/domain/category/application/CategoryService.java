package com.aicheck.business.domain.category.application;

import com.aicheck.business.domain.category.presentation.FirstCategoryWithSecondResponse;
import com.aicheck.business.domain.category.repository.FirstCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final FirstCategoryRepository firstCategoryRepository;

    public List<FirstCategoryWithSecondResponse> getAllWithChildren() {
        return firstCategoryRepository.findAllWithSecondCategories().stream()
                .map(FirstCategoryWithSecondResponse::from)
                .toList();
    }
}


