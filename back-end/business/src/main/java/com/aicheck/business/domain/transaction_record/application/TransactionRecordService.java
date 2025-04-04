package com.aicheck.business.domain.transaction_record.application;

import com.aicheck.business.domain.transaction_record.application.dto.CalendarRecordListResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.Interval;
import com.aicheck.business.domain.transaction_record.presentation.dto.MemberTransactionRecords;
import com.aicheck.business.domain.transaction_record.presentation.dto.RatingRequest;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionInfoResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordDetailResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordListResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.UpdateTransactionRecordRequest;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRecordService {
    TransactionRecordListResponse getTransactionRecords(Long memberId, LocalDate startDate, LocalDate endDate,
                                                        String type);

    TransactionRecordListResponse getChildTransactionRecords(Long memberId, Long childId, LocalDate startDate,
                                                             LocalDate endDate, String type);

    CalendarRecordListResponse getCalendarData(Long memberId, int year, int month);

    TransactionRecordDetailResponse getTransactionRecordDetail(Long recordId);

    void updateTransactionRecord(UpdateTransactionRecordRequest request);

    void rateTransaction(RatingRequest request);

    List<MemberTransactionRecords> getTransactionRecords();

    TransactionInfoResponse getTransactionInfo(Long memberId, LocalDate startDate, Interval interval);

    void saveWithdrawTransaction(Long memberId, String displayName, Long amount);

    void saveDepositTransaction(Long memberId, String displayName, Long amount);
}

