package com.aicheck.batch.domain.report.summary.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategorySummary {
    Long firstCategoryId;
    String displayName;
    int amount;
    double percentage;
    List<SubCategorySummary> subCategories; // optional
}
