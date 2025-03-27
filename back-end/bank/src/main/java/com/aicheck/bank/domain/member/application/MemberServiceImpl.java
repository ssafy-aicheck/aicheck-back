package com.aicheck.bank.domain.member.application;

import com.aicheck.bank.domain.member.dto.FindBankMemberFeignResponse;
import com.aicheck.bank.domain.member.entity.Member;
import com.aicheck.bank.domain.member.repository.MemberRepository;
import com.aicheck.bank.global.exception.BankErrorCodes;
import com.aicheck.bank.global.exception.BankException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public FindBankMemberFeignResponse findBankMemberByEmail(String email) {
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new BankException(BankErrorCodes.BANK_MEMBER_NOT_FOUND));
        return FindBankMemberFeignResponse.from(member);
    }

}
