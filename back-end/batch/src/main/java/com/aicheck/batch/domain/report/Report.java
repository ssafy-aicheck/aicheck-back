package com.aicheck.batch.domain.report;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    private String reportId;
    private int year;
    private int month;
    private Long childId;
    private Long totalAmount;

}