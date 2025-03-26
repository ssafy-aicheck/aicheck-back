package com.aicheck.bank.domain.member.application;

import com.aicheck.bank.domain.member.dto.FindBankMemberFeignResponse;

public interface MemberService {
    FindBankMemberFeignResponse findBankMemberByEmail(String email);
}
