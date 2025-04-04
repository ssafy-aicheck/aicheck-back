package com.aicheck.business.domain.transaction_record.repository;

import com.aicheck.business.domain.transaction_record.entity.TransactionRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {
    List<TransactionRecord> findByMemberIdAndCreatedAtBetweenAndDeletedAtIsNull(Long memberId, LocalDateTime start,
                                                                                LocalDateTime end);

    Optional<TransactionRecord> findByIdAndDeletedAtIsNull(Long recordId);

    @Query("SELECT t FROM TransactionRecord t " +
            "WHERE YEAR(t.createdAt) = :year " +
            "AND MONTH(t.createdAt) = :month " +
            "AND t.deletedAt IS NULL " +
            "AND t.type = com.aicheck.business.domain.transaction_record.entity.TransactionType.PAYMENT")
    List<TransactionRecord> findByYearAndMonth(@Param("year") int year, @Param("month") int month);


}
