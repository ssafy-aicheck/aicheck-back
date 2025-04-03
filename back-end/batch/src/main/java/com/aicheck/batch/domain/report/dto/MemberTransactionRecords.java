package com.aicheck.batch.domain.report.dto;

import com.aicheck.batch.domain.report.presentation.dto.TransactionRecordDetailResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberTransactionRecords {
    private Long memberId;
    private LocalDate birth;
    private List<TransactionRecordDetailResponse> records;
}
