package com.aicheck.batch.domain.report.application.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class MemberInfoResponse {
    private String name;
    private LocalDate birth;
}
