package com.aicheck.bank.member.application;

import com.aicheck.bank.member.dto.FindBankMemberFeignResponse;
import com.aicheck.bank.member.entity.Member;
import com.aicheck.bank.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public FindBankMemberFeignResponse findBankMemberByEmail(String email) {
        Member member = memberRepository.findMemberByEmail(email).orElse(null);
        if (member == null) {
            return null;
        }
        return FindBankMemberFeignResponse.from(member);
    }

}
