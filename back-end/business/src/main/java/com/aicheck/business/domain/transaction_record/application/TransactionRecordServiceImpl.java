package com.aicheck.business.domain.transaction_record.application;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.category.entity.FirstCategory;
import com.aicheck.business.domain.category.entity.SecondCategory;
import com.aicheck.business.domain.category.repository.FirstCategoryRepository;
import com.aicheck.business.domain.category.repository.SecondCategoryRepository;
import com.aicheck.business.domain.transaction_record.application.dto.CalendarRecordItem;
import com.aicheck.business.domain.transaction_record.application.dto.CalendarRecordListResponse;
import com.aicheck.business.domain.transaction_record.entity.TransactionRecord;
import com.aicheck.business.domain.transaction_record.entity.TransactionType;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordDetailResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordListResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.UpdateTransactionRecordRequest;
import com.aicheck.business.domain.transaction_record.repository.TransactionRecordQueryRepository;
import com.aicheck.business.domain.transaction_record.repository.TransactionRecordRepository;
import com.aicheck.business.global.error.BusinessErrorCodes;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionRecordServiceImpl implements TransactionRecordService {

    private final MemberRepository memberRepository;
    private final TransactionRecordQueryRepository transactionRecordQueryRepository;
    private final TransactionRecordRepository transactionRecordRepository;
    private final FirstCategoryRepository firstCategoryRepository;
    private final SecondCategoryRepository secondCategoryRepository;

    @Override
    public TransactionRecordListResponse getTransactionRecords(Long memberId, LocalDate startDate, LocalDate endDate,
                                                               String type) {
        TransactionType transactionType = getTransactionType(type);
        return transactionRecordQueryRepository.findTransactionRecords(memberId, startDate, endDate, transactionType);
    }

    @Override
    public TransactionRecordListResponse getChildTransactionRecords(Long memberId, Long childId, LocalDate startDate,
                                                                    LocalDate endDate, String type) {
        TransactionType transactionType = getTransactionType(type);
        Member child = memberRepository.findById(childId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
        if (!memberId.equals(child.getManagerId())) {
            throw new BusinessException(BusinessErrorCodes.NOT_YOUR_CHILD);
        }
        return transactionRecordQueryRepository.findTransactionRecords(childId, startDate, endDate, transactionType);
    }

    @Override
    public CalendarRecordListResponse getCalendarData(Long memberId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<TransactionRecord> records = transactionRecordRepository
                .findByMemberIdAndCreatedAtBetweenAndDeletedAtIsNull(memberId, startDateTime, endDateTime);

        Map<LocalDate, Long> sumByDate = records.stream()
                .collect(Collectors.groupingBy(
                        record -> record.getCreatedAt().toLocalDate(),
                        Collectors.summingLong(record -> {
                            switch (record.getType()) {
                                case DEPOSIT, INBOUND_TRANSFER -> {
                                    return record.getAmount();
                                }
                                case PAYMENT, WITHDRAW, OUTBOUND_TRANSFER -> {
                                    return -record.getAmount();
                                }
                                default -> {
                                    return 0L;
                                }
                            }
                        })
                ));

        List<CalendarRecordItem> calendar = sumByDate.entrySet().stream()
                .map(entry -> CalendarRecordItem.builder()
                        .date(entry.getKey())
                        .sum(entry.getValue())
                        .build())
                .sorted(Comparator.comparing(CalendarRecordItem::getDate))
                .toList();

        return CalendarRecordListResponse.builder()
                .calendar(calendar)
                .build();
    }

    @Override
    public TransactionRecordDetailResponse getTransactionRecordDetail(Long recordId) {
        TransactionRecord record = transactionRecordRepository.findByIdAndDeletedAtIsNull(recordId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.TRANSACTION_RECORD_NOT_FOUND));

        return TransactionRecordDetailResponse.from(record);
    }

    @Override
    @Transactional
    public void updateTransactionRecord(UpdateTransactionRecordRequest request) {
        TransactionRecord record = transactionRecordRepository.findById(request.getRecordId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.TRANSACTION_RECORD_NOT_FOUND));

        FirstCategory firstCategory = firstCategoryRepository.findById(request.getFirstCategoryId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.FIRST_CATEGORY_NOT_FOUND));

        SecondCategory secondCategory = secondCategoryRepository
                .findByIdAndFirstCategoryId(request.getSecondCategoryId(), request.getFirstCategoryId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.SECOND_CATEGORY_NOT_FOUND_OR_INVALID));

        record.updateCategoryAndDescription(
                firstCategory,
                secondCategory,
                request.getDescription()
        );
    }

    public TransactionType getTransactionType(String type) {
        if (type != null && !type.isBlank()) {
            try {
                return TransactionType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("올바르지 않은 거래 타입입니다: " + type);
            }
        }
        return null;
    }

}
