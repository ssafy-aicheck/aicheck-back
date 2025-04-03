package com.aicheck.batch.domain.report.repository;

import com.aicheck.batch.domain.report.entity.MonthlyReport;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<MonthlyReport, String> {
    Optional<MonthlyReport> findByYearAndMonthAndChildId(int year, int month, Long childId);
}