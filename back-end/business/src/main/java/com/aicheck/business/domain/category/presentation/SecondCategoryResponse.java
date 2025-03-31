package com.aicheck.business.domain.category.presentation;

import com.aicheck.business.domain.category.entity.SecondCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SecondCategoryResponse {

    private Long id;
    private String name;
    private String displayName;

    public static SecondCategoryResponse from(SecondCategory entity) {
        return SecondCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .displayName(entity.getDisplayName())
                .build();
    }
}
