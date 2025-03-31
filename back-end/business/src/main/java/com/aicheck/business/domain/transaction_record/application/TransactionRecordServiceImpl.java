package com.aicheck.business.domain.transaction_record.application;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionRecordListResponse;
import com.aicheck.business.domain.transaction_record.repository.TransactionRecordQueryRepository;
import com.aicheck.business.domain.transaction_record.entity.TransactionType;
import com.aicheck.business.global.error.BusinessErrorCodes;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionRecordServiceImpl implements TransactionRecordService {

    private final MemberRepository memberRepository;
    private final TransactionRecordQueryRepository transactionRecordQueryRepository;

    @Override
    public TransactionRecordListResponse getTransactionRecords(Long memberId, LocalDate startDate, LocalDate endDate, String type) {
        TransactionType transactionType = getTransactionType(type);
        return transactionRecordQueryRepository.findTransactionRecords(memberId, startDate, endDate, transactionType);
    }

    @Override
    public TransactionRecordListResponse getChildTransactionRecords(Long memberId, Long childId, LocalDate startDate,
                                                                    LocalDate endDate, String type) {
        TransactionType transactionType = getTransactionType(type);
        Member child = memberRepository.findById(childId).orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
        if(!memberId.equals(child.getManagerId())) {
            throw new BusinessException(BusinessErrorCodes.NOT_YOUR_CHILD);
        }
        return transactionRecordQueryRepository.findTransactionRecords(childId, startDate, endDate, transactionType);
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
