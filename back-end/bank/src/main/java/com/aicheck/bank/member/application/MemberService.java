package com.aicheck.bank.member.application;

import com.aicheck.bank.member.dto.FindBankMemberFeignResponse;

public interface MemberService {
    FindBankMemberFeignResponse findBankMemberByEmail(String email);
}
