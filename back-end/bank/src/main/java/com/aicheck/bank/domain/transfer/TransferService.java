package com.aicheck.bank.domain.transfer;

import com.aicheck.bank.domain.account.entity.Account;
import com.aicheck.bank.domain.account.infrastructure.repository.AccountRepository;
import com.aicheck.bank.global.exception.BankErrorCodes;
import com.aicheck.bank.global.exception.BankException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransferService {

    private final AccountRepository accountRepository;

    @Transactional
    public void executeTransfer(TransferExecuteRequest request) {
        Account fromAccount = accountRepository.findWithLockByAccountNo(request.getFromAccountNo())
                .orElseThrow(() -> new BankException(BankErrorCodes.ACCOUNT_NOT_FOUND));
        Account toAccount = accountRepository.findWithLockByAccountNo(request.getToAccountNo())
                .orElseThrow(() -> new BankException(BankErrorCodes.ACCOUNT_NOT_FOUND));

        fromAccount.withdraw(request.getAmount());
        toAccount.deposit(request.getAmount());
    }

}
