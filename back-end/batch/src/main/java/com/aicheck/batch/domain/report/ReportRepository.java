package com.aicheck.batch.domain.report;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    Optional<Report> findByYearAndMonthAndChildId(int year, int month, String childId);
}