package com.aicheck.bank.domain.account.application;

import com.aicheck.bank.domain.account.dto.FindAccountsFeignResponse;
import com.aicheck.bank.domain.account.entity.Account;
import com.aicheck.bank.domain.account.repository.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<FindAccountsFeignResponse> findMyAccountsByMemberId(Long memberId) {
        List<Account> accounts = accountRepository.findAllByMemberId(memberId);
        return accounts.stream().map(FindAccountsFeignResponse::from).toList();
    }
}
