package com.aicheck.business.domain.transaction_record.presentation.dto;

import com.aicheck.business.domain.transaction_record.entity.TransactionRecord;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionRecordDetailResponse {
    private Long recordId;
    private String firstCategoryName;
    private String secondCategoryName;
    private String displayName;
    private String type;
    private Integer amount;
    private String description;
    private Integer rating;
    private String createdAt;

    public static TransactionRecordDetailResponse from(TransactionRecord record) {
        return TransactionRecordDetailResponse.builder()
                .recordId(record.getId())
                .firstCategoryName(record.getFirstCategoryName())
                .secondCategoryName(record.getSecondCategoryName())
                .displayName(record.getDisplayName())
                .type(record.getType().name())
                .amount(record.getAmount())
                .description(record.getDescription())
                .rating(record.getRating())
                .createdAt(record.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
