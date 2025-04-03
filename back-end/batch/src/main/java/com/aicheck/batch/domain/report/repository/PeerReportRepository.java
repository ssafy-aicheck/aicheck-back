package com.aicheck.batch.domain.report.repository;

import com.aicheck.batch.domain.report.entity.MonthlyPeerReport;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PeerReportRepository extends MongoRepository<MonthlyPeerReport, String> {
    Optional<MonthlyPeerReport> findByPeerGroupAndYearAndMonth(String peerGroup, int year, int month);

    boolean existsByPeerGroupAndYearAndMonth(String peerGroup, int year, int month);
}

