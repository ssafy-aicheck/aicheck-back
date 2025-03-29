package com.aicheck.batch.domain.schedule.dto;

import com.aicheck.batch.domain.schedule.entity.Schedule;
import com.aicheck.batch.domain.schedule.entity.Schedule.Interval;
import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegisterScheduledTransferRequest {
    private Long childId;
    private Long amount;
    private String interval;
    private LocalDate startDate;

    public static Schedule toEntity(Long memberId,
                                    String parentAccountNo,
                                    String childAccountNo,
                                    RegisterScheduledTransferRequest request) {
        return Schedule.builder()
                .memberId(memberId)
                .parentAccountNo(parentAccountNo)
                .childAccountNo(childAccountNo)
                .amount(request.getAmount())
                .interval(Interval.valueOf(request.getInterval()))
                .startDate(request.getStartDate())
                .build();
    }

}
