package com.aicheck.business.domain.transaction_record.application;

import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordListResponse;
import com.aicheck.business.domain.transaction_record.repository.TransactionRecordQueryRepository;
import com.aicheck.business.domain.transaction_record.entity.TransactionType;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionRecordServiceImpl implements TransactionRecordService {

    private final TransactionRecordQueryRepository transactionRecordQueryRepository;

    @Override
    public TransactionRecordListResponse getTransactionRecords(Long memberId, LocalDate startDate, LocalDate endDate, String type) {

        TransactionType transactionType = null;
        if (type != null && !type.isBlank()) {
            try {
                transactionType = TransactionType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("올바르지 않은 거래 타입입니다: " + type);
            }
        }

        return transactionRecordQueryRepository.findTransactionRecords(memberId, startDate, endDate, transactionType);
    }
}


