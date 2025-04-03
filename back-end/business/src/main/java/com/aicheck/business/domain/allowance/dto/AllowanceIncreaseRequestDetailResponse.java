package com.aicheck.business.domain.allowance.dto;

import com.aicheck.business.domain.allowance.entity.AllowanceIncreaseRequest;
import com.aicheck.business.domain.allowance.enums.AllowanceRequestType;
import com.aicheck.business.domain.auth.domain.entity.Member;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllowanceIncreaseRequestDetailResponse {
    private Long id;
    private String type;
    private String status;
    private Long reportId;
    private Long childId;
    private String childName;
    private String image;
    private Integer prevAmount;
    private Integer afterAmount;
    private String description;
    private LocalDateTime createdAt;

    public static AllowanceIncreaseRequestDetailResponse from(Member child, AllowanceIncreaseRequest request) {
        return AllowanceIncreaseRequestDetailResponse.builder()
                .id(request.getId())
                .type(AllowanceRequestType.INCREASE.name())
                .status(request.getStatus().name())
                .reportId(request.getReportId())
                .childId(child.getId())
                .childName(child.getName())
                .image(child.getProfileUrl())
                .prevAmount(request.getBeforeAmount())
                .afterAmount(request.getAfterAmount())
                .description(request.getDescription())
                .createdAt(request.getCreatedAt())
                .build();
    }

}
