package com.aicheck.business.domain.transaction_record.application.dto;

import com.aicheck.business.domain.transaction_record.entity.TransactionRecord;
import com.aicheck.business.domain.transaction_record.entity.TransactionType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionRecordItem {
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
    private String time;
    private LocalDateTime createdAt;

    public static TransactionRecordItem from(TransactionRecord entity) {
        return TransactionRecordItem.builder()
                .recordId(entity.getId())
                .firstCategoryId(entity.getFirstCategory().getId())
                .firstCategoryName(entity.getFirstCategory().getDisplayName())
                .secondCategoryId(entity.getSecondCategory().getId())
                .secondCategoryName(entity.getSecondCategory().getDisplayName())
                .displayName(entity.getDisplayName())
                .type(parseType(entity.getType()))
                .amount(entity.getAmount().longValue())
                .description(entity.getDescription())
                .rating(entity.getRating())
                .time(entity.getCreatedAt().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private static String parseType(TransactionType type) {
        return switch (type) {
            case DEPOSIT, INBOUND_TRANSFER -> "INCOME";
            case PAYMENT, WITHDRAW, OUTBOUND_TRANSFER -> "EXPENSE";
        };
    }

}

