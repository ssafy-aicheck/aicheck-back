package com.aicheck.business.domain.member.presentation.dto;

import com.aicheck.business.domain.auth.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildrenProfileResponse {
    private Long childId;
    private String name;
    private String image;

    public static ChildrenProfileResponse from(Member member) {
        return ChildrenProfileResponse.builder()
                .childId(member.getId())
                .name(member.getName())
                .image(member.getProfileUrl())
                .build();
    }

}
