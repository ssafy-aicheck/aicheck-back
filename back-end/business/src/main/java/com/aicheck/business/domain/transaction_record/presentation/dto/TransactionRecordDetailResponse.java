package com.aicheck.business.domain.transaction_record.presentation.dto;

import com.aicheck.business.domain.transaction_record.entity.TransactionRecord;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TransactionRecordDetailResponse {
    private Long recordId;
    private Integer firstCategoryId;
    private String firstCategoryName;
    private Integer secondCategoryId;
    private String secondCategoryName;
    private String displayName;
    private String type;
    private Long amount;
    private String description;
    private Integer rating;
    private String createdAt;

    public static TransactionRecordDetailResponse from(TransactionRecord record) {
        return TransactionRecordDetailResponse.builder()
                .recordId(record.getId())
                .firstCategoryId(record.getFirstCategory() == null ? null : record.getFirstCategory().getId())
                .firstCategoryName(record.getFirstCategory() == null ? null : record.getFirstCategory().getDisplayName())
                .secondCategoryId(record.getSecondCategory() == null ? null : record.getSecondCategory().getId())
                .secondCategoryName(record.getSecondCategory() == null ? null : record.getSecondCategory().getDisplayName())
                .displayName(record.getDisplayName())
                .type(record.getType().name())
                .amount(record.getAmount())
                .description(record.getDescription())
                .rating(record.getRating())
                .createdAt(record.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
