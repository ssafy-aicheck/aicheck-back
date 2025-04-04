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

@Document(collection = "monthly_reports")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyReport {

    @Id
    private String id;

    private Long childId;
    private int year;
    private int month;
    private int totalAmount;

    private List<CategorySummary> categories;

    private LocalDateTime createdAt;

}