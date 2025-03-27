package com.aicheck.business.domain.account.application.service;

import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import com.aicheck.business.domain.account.infrastructure.client.BankClient;
import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final MemberRepository memberRepository;
    private final BankClient bankAccountClient;

    public List<FindAccountFeignResponse> findMyAccounts(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<FindAccountFeignResponse> accounts = bankAccountClient.findMyAccounts(member.getBankMemberId());
        return accounts;
    }

}
