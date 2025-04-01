package com.aicheck.business.domain.category.presentation;

import com.aicheck.business.domain.category.entity.FirstCategory;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FirstCategoryWithSecondResponse {
    private Integer id;
    private String displayName;
    private List<SecondCategoryResponse> secondCategories;

    public static FirstCategoryWithSecondResponse from(FirstCategory entity) {
        return FirstCategoryWithSecondResponse.builder()
                .id(entity.getId())
                .displayName(entity.getDisplayName())
                .secondCategories(
                        entity.getSecondCategories().stream()
                                .map(SecondCategoryResponse::from)
                                .toList()
                )
                .build();
    }
}