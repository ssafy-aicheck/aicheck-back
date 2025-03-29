package com.aicheck.batch.domain.schedule.dto;

import com.aicheck.batch.domain.schedule.entity.Schedule;
import com.aicheck.batch.domain.schedule.entity.Schedule.Interval;
import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegisterScheduledTransferRequest {
    private Long memberId;
    private String parentAccountNo;
    private String childAccountNo;
    private Long amount;
    private String interval;
    private LocalDate startDate;

    public static Schedule toEntity(Long memberId, RegisterScheduledTransferRequest request) {
        return Schedule.builder()
                .memberId(memberId)
                .parentAccountNo(request.getParentAccountNo())
                .childAccountNo(request.getChildAccountNo())
                .amount(request.getAmount())
                .interval(Interval.valueOf(request.getInterval()))
                .startDate(request.getStartDate())
                .build();
    }

}
