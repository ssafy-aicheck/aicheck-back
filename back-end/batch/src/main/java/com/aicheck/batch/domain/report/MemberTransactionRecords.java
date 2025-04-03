package com.aicheck.batch.domain.report;

import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberTransactionRecords {
    private Long memberId;
    private List<TransactionRecordDetailResponse> records;
}
