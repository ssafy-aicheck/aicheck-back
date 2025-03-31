package com.aicheck.business.domain.transaction_record.repository;

import com.aicheck.business.domain.transaction_record.entity.TransactionRecord;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {
    List<TransactionRecord> findByMemberIdAndCreatedAtBetweenAndDeletedAtIsNull(Long memberId, LocalDateTime start, LocalDateTime end);
}
