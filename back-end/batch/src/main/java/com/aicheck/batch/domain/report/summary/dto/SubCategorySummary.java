package com.aicheck.batch.domain.report.summary.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubCategorySummary {
    Long secondCategoryId;
    String displayName;
    int amount;
    double percentage;
}
