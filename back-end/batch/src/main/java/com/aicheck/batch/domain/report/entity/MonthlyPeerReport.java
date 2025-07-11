package com.aicheck.batch.domain.report.entity;

import com.aicheck.batch.domain.report.summary.dto.CategorySummary;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "monthly_peer_reports")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyPeerReport {

    @Id
    private String id;

    private String peerGroup;
    private int year;
    private int month;
    private int totalAmount;

    private List<CategorySummary> categories;

    private LocalDateTime createdAt;
}
