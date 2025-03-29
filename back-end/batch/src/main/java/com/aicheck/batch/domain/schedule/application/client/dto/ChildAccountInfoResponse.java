package com.aicheck.batch.domain.schedule.application.client.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChildAccountInfoResponse {
    private Long memberId;
    private String name;
    private String image;
    private String accountNo;
    private String accountName;
    private Long balance;
}
