package com.aicheck.bank.domain.member.application;

import static com.aicheck.bank.domain.member.entity.Member.Gender.MALE;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.bank.domain.account.entity.Account;
import com.aicheck.bank.domain.account.infrastructure.repository.AccountRepository;
import com.aicheck.bank.domain.member.entity.Member;
import com.aicheck.bank.domain.member.presentation.dto.request.SignUpRequest;
import com.aicheck.bank.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class SignUpService {
	private final MemberRepository memberRepository;
	private final AccountRepository accountRepository;

	public void signup(final SignUpRequest request) {
		Member member = Member.builder()
			.birth(request.brith())
			.name(request.name())
			.email(request.email())
			.gender(MALE)
			.createdAt(LocalDateTime.now())
			.build();

		Account account = Account.builder()
			.member(member)
			.accountName("싸피입출금계좌")
			.accountNo(request.accountNo())
			.password("1234")
			.balance(1_000_000L)
			.createdAt(LocalDateTime.now())
			.build();

		memberRepository.save(member);
		accountRepository.save(account);
	}
}
