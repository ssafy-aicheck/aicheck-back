package com.aicheck.business.domain.transaction_record.application.dto;

import com.aicheck.business.domain.transaction_record.entity.TransactionRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionRecordItem {
    private Long recordId;
    private String firstCategoryName;
    private String secondCategoryName;
    private String displayName;
    private String type;
    private Long amount;
    private String description;
    private Integer rating;
    private String time;
    private LocalDateTime createdAt;

    public static TransactionRecordItem from(TransactionRecord entity) {
        return TransactionRecordItem.builder()
                .recordId(entity.getId())
                .firstCategoryName(entity.getFirstCategoryName())
                .secondCategoryName(entity.getSecondCategoryName())
                .displayName(entity.getDisplayName())
                .type(entity.getType().name())
                .amount(entity.getAmount().longValue())
                .description(entity.getDescription())
                .rating(entity.getRating())
                .time(entity.getCreatedAt().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .createdAt(entity.getCreatedAt())
                .build();
    }

}

