package com.aicheck.bank.domain.account.application;

import com.aicheck.bank.domain.account.dto.AccountInfoFeignResponse;
import com.aicheck.bank.domain.account.dto.FindAccountsFeignResponse;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignRequest;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignResponse;
import com.aicheck.bank.domain.account.dto.VerifyAccountPasswordFeignRequest;
import com.aicheck.bank.domain.account.dto.VerifyAccountPasswordFeignResponse;
import com.aicheck.bank.domain.account.entity.Account;
import com.aicheck.bank.domain.account.infrastructure.feign.dto.TransferSenderResponse;
import com.aicheck.bank.domain.account.infrastructure.feign.dto.TransferReceiverResponse;
import com.aicheck.bank.domain.account.infrastructure.repository.AccountRepository;
import com.aicheck.bank.domain.member.entity.Member;
import com.aicheck.bank.global.exception.BankErrorCodes;
import com.aicheck.bank.global.exception.BankException;
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

    @Override
    public VerifyAccountPasswordFeignResponse verifyAccountPassword(VerifyAccountPasswordFeignRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new BankException(BankErrorCodes.ACCOUNT_NOT_FOUND));

        if (!account.getPassword().equals(request.getPassword())) {
            throw new BankException(BankErrorCodes.INVALID_PASSWORD);
        }

        return VerifyAccountPasswordFeignResponse.builder()
                .verified(true)
                .accountNo(account.getAccountNo())
                .build();
    }

    @Override
    public AccountInfoFeignResponse getAccountInfoByNumber(String accountNo) {
        Account account = accountRepository.findAccountByAccountNo(accountNo)
                .orElseThrow(() -> new BankException(BankErrorCodes.ACCOUNT_NOT_FOUND));
        return AccountInfoFeignResponse.from(account);
    }

    @Override
    public VerifyAccountFeignResponse verifyAccount(VerifyAccountFeignRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new BankException(BankErrorCodes.ACCOUNT_NOT_FOUND));
        if(!account.getMember().getId().equals(request.getBankMemberId())) {
            throw new BankException(BankErrorCodes.NOT_YOUR_ACCOUNT);
        }
        return VerifyAccountFeignResponse.builder()
                .verified(true)
                .accountNo(account.getAccountNo())
                .build();
    }

    @Override
    public List<AccountInfoFeignResponse> findChildrenAccountInfos(List<String> accountNos) {
        List<Account> accounts = accountRepository.findByAccountNoIn(accountNos);
        List<AccountInfoFeignResponse> feignResponses = accounts.stream().map(AccountInfoFeignResponse::from).toList();

        return feignResponses;
    }

    @Override
    public TransferReceiverResponse findTransferReceiverInfo(String accountNo) {
        Account account = accountRepository.findAccountByAccountNo(accountNo)
                .orElseThrow(() -> new BankException(BankErrorCodes.ACCOUNT_NOT_FOUND));
        Member member = account.getMember();

        return TransferReceiverResponse.builder()
                .accountNo(account.getAccountNo())
                .name(member.getName())
                .build();
    }

    @Override
    public TransferSenderResponse findSenderAccountInfo(String accountNo) {
        Account account = accountRepository.findAccountByAccountNo(accountNo)
                .orElseThrow(() -> new BankException(BankErrorCodes.ACCOUNT_NOT_FOUND));

        return TransferSenderResponse.builder()
                .accountName(account.getAccountName())
                .accountNo(account.getAccountNo())
                .balance(account.getBalance())
                .build();
    }
}
