package com.aicheck.bank.domain.account.application;

import com.aicheck.bank.domain.account.dto.FindAccountsFeignResponse;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignRequest;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignResponse;
import com.aicheck.bank.domain.account.entity.Account;
import com.aicheck.bank.domain.account.repository.AccountRepository;
import com.aicheck.bank.domain.member.entity.Member;
import com.aicheck.bank.domain.member.repository.MemberRepository;
import com.aicheck.bank.global.exception.BankErrorCodes;
import com.aicheck.bank.global.exception.BankException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<FindAccountsFeignResponse> findMyAccountsByMemberId(Long memberId) {
        List<Account> accounts = accountRepository.findAllByMemberId(memberId);
        return accounts.stream().map(FindAccountsFeignResponse::from).toList();
    }

    @Override
    public VerifyAccountFeignResponse verifyAccount(VerifyAccountFeignRequest request) {
        Member member = memberRepository.findById(request.getBankMemberId())
                .orElseThrow(() -> new BankException(BankErrorCodes.BANK_MEMBER_NOT_FOUND));

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new BankException(BankErrorCodes.ACCOUNT_NOT_FOUND));

        if (member.getId() != account.getMemberId()) {
            throw new BankException(BankErrorCodes.NOT_YOUR_ACCOUNT);
        }

        if (!account.getPassword().equals(request.getPassword())) {
            throw new BankException(BankErrorCodes.INVALID_PASSWORD);
        }

        return VerifyAccountFeignResponse.builder()
                .accountNo(account.getAccountNo())
                .build();
    }
}
