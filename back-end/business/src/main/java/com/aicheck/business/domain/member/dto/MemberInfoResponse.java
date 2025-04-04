package com.aicheck.business.domain.member.dto;

import com.aicheck.business.domain.auth.domain.entity.Member;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponse {
    private String name;
    private LocalDate birth;

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
                .name(member.getName())
                .birth(member.getBirth())
                .build();
    }

}
