package com.aicheck.business.domain.transaction_record.presentation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateTransactionRecordRequest {
    @NotNull
    private Long recordId;

    @NotNull
    private Integer firstCategoryId;

    private Integer secondCategoryId;

    @Size(max = 60)
    private String description;
}
