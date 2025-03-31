package com.aicheck.business.domain.category.presentation;

import com.aicheck.business.domain.category.entity.FirstCategory;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private String displayName;
    private List<SecondCategoryResponse> secondCategories;

    public static CategoryResponse from(FirstCategory entity) {
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .displayName(entity.getDisplayName())
                .secondCategories(
                        entity.getSecondCategories().stream()
                                .map(SecondCategoryResponse::from)
                                .toList()
                )
                .build();
    }
}
