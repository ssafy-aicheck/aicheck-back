package com.aicheck.batch.domain.report;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TransactionRecordDetailResponse {
    private Long recordId;
    private Integer firstCategoryId;
    private String firstCategoryName;
    private Integer secondCategoryId;
    private String secondCategoryName;
    private String displayName;
    private String type;
    private Integer amount;
    private String description;
    private Integer rating;
    private String createdAt;

}
