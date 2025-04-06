package com.aicheck.business.domain.transaction_record.repository;

import com.aicheck.business.domain.transaction_record.application.dto.TransactionRecordItem;
import com.aicheck.business.domain.transaction_record.entity.QTransactionRecord;
import com.aicheck.business.domain.transaction_record.entity.TransactionType;
import com.aicheck.business.domain.transaction_record.presentation.dto.DailyTransactionRecords;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordListResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TransactionRecordQueryRepositoryImpl implements TransactionRecordQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final QTransactionRecord q = QTransactionRecord.transactionRecord;

    public TransactionRecordListResponse findTransactionRecords(Long memberId, LocalDate startDate, LocalDate endDate,
                                                                List<TransactionType> types) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        var query = queryFactory.selectFrom(q)
                .where(
                        q.memberId.eq(memberId),
                        q.createdAt.between(start, end),
                        q.deletedAt.isNull(),
                        types != null ? q.type.in(types) : null
                )
                .orderBy(q.createdAt.asc());

        List<TransactionRecordItem> items = query.fetch()
                .stream()
                .map(TransactionRecordItem::from)
                .toList();

        // 날짜 기준으로 묶기
        Map<LocalDate, List<TransactionRecordItem>> grouped = items.stream()
                .collect(Collectors.groupingBy(item -> item.getCreatedAt().toLocalDate()));

        List<DailyTransactionRecords> dailyList = grouped.entrySet().stream()
                .map(entry -> DailyTransactionRecords.builder()
                        .date(entry.getKey())
                        .records(entry.getValue())
                        .build())
                .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
                .toList();

        return TransactionRecordListResponse.builder()
                .data(dailyList)
                .build();
    }
}
