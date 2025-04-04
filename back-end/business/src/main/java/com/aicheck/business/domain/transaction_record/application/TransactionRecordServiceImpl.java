package com.aicheck.business.domain.transaction_record.application;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.entity.MemberType;
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
import com.aicheck.business.domain.transaction_record.presentation.dto.MemberTransactionRecords;
import com.aicheck.business.domain.transaction_record.presentation.dto.Interval;
import com.aicheck.business.domain.transaction_record.presentation.dto.RatingRequest;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionInfoResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordDetailResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordDto;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordListResponse;
import com.aicheck.business.domain.transaction_record.presentation.dto.UpdateTransactionRecordRequest;
import com.aicheck.business.domain.transaction_record.repository.TransactionRecordQueryRepository;
import com.aicheck.business.domain.transaction_record.repository.TransactionRecordRepository;
import com.aicheck.business.global.error.BusinessErrorCodes;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Transactional
    public void rateTransaction(RatingRequest request) {
        TransactionRecord record = transactionRecordRepository.findById(request.getRecordId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.TRANSACTION_RECORD_NOT_FOUND));
        record.updateRating(request.getRating());
    }

    @Override
    public List<MemberTransactionRecords> getTransactionRecords() {
        List<Member> children = memberRepository.findMembersByTypeAndDeletedAtIsNull(MemberType.CHILD);

        LocalDate now = LocalDate.now();
        YearMonth lastMonth = YearMonth.from(now.minusMonths(1));

        int year = lastMonth.getYear();
        int month = lastMonth.getMonthValue();

        List<TransactionRecord> records = transactionRecordRepository.findByYearAndMonth(year, month);

        List<MemberTransactionRecords> memberTransactionRecords = new ArrayList<>();
        for (Member child : children) {
            List<TransactionRecordDetailResponse> transactionDetails = new ArrayList<>();
            records.stream().filter(record -> record.getMemberId() == child.getId())
                    .forEach(record -> transactionDetails.add(TransactionRecordDetailResponse.from(record)));
            memberTransactionRecords.add(MemberTransactionRecords.builder()
                    .memberId(child.getId())
                    .birth(child.getBirth())
                    .records(transactionDetails)
                    .build());
        }

        return memberTransactionRecords;
    }

    public TransactionInfoResponse getTransactionInfo(Long memberId, LocalDate startDate, Interval interval) {
        LocalDate today = LocalDate.now();
        Period period = getPeriodByInterval(interval);

        while (!startDate.plus(period).isAfter(today)) {
            startDate = startDate.plus(period);
        }

        List<TransactionRecord> records = transactionRecordRepository
            .findByMemberIdAndCreatedAtBetweenAndDeletedAtIsNull(
                memberId,
                startDate.atStartOfDay(),
                today.plusDays(1).atStartOfDay()
            );

        double averageScore = records.stream()
            .map(TransactionRecord::getRating)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0);

        return TransactionInfoResponse.of(
            (float) (Math.round(averageScore * 10) / 10.0),
            records.stream()
                .map(TransactionRecordDto::from)
                .toList()
        );
    }

    private Period getPeriodByInterval(Interval interval) {
        return switch (interval) {
            case WEEKLY -> Period.ofWeeks(1);
            case BIWEEKLY -> Period.ofWeeks(2);
            case MONTHLY -> Period.ofMonths(1);
        };
    }
}
