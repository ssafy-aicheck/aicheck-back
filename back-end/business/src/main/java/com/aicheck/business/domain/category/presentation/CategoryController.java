package com.aicheck.business.domain.category.presentation;

import com.aicheck.business.domain.category.application.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<FirstCategoryWithSecondResponse> getAllCategories() {
        return categoryService.getAllWithChildren();
    }
}
