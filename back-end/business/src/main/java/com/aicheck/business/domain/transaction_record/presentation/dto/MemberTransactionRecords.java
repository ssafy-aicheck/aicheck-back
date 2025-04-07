package com.aicheck.business.domain.transaction_record.presentation.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MemberTransactionRecords {
    private Long memberId;
    private String name;
    private Long managerId;
    private LocalDate birth;
    private List<TransactionRecordDetailResponse> records;
}
