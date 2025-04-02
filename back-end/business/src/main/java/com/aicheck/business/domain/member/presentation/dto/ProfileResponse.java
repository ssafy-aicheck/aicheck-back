package com.aicheck.business.domain.member.presentation.dto;

import com.aicheck.business.domain.account.dto.AccountInfoResponse;
import com.aicheck.business.domain.auth.domain.entity.Member;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponse {
    private Long memberId;
    private String name;
    private LocalDate birth;
    private String image;
    private String type;
    private AccountDto account;

    @Getter
    @Builder
    public static class AccountDto {
        private Integer id;
        private String name;
        private String no;
    }

    public static ProfileResponse from(Member member, AccountInfoResponse accountInfo) {
        return ProfileResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .birth(member.getBirth())
                .image(member.getProfileUrl())
                .type(member.getType().name())
                .account(AccountDto.builder()
                        .id(accountInfo == null ? null : accountInfo.getAccountId())
                        .name(accountInfo == null ? null : accountInfo.getAccountName())
                        .no(accountInfo == null ? null : accountInfo.getAccountNo())
                        .build())
                .build();
    }

}
