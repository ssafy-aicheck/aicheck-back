package com.aicheck.bank.domain.member.application;

import static com.aicheck.bank.global.exception.BankErrorCodes.DUPLICATED_ACCOUNT_NO;

import org.springframework.stereotype.Service;

import com.aicheck.bank.domain.account.infrastructure.repository.AccountRepository;
import com.aicheck.bank.global.exception.BankException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountCheckService {

	private final AccountRepository accountRepository;

	public void accountCheck(String accountNo) {
		if (accountRepository.findByAccountNo(accountNo).isPresent()) {
			throw new BankException(DUPLICATED_ACCOUNT_NO);
		}
	}
}
