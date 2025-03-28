package com.aicheck.business.domain.account.application.service;

import com.aicheck.business.domain.account.dto.AccountInfoResponse;
import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import com.aicheck.business.domain.account.dto.VerifyAccountPasswordRequest;
import com.aicheck.business.domain.account.dto.RegisterMainAccountRequest;
import com.aicheck.business.domain.account.dto.VerifyAccountResponse;
import com.aicheck.business.domain.account.infrastructure.client.BankClient;
import com.aicheck.business.domain.account.infrastructure.client.dto.VerifyAccountFeignRequest;
import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.global.error.BusinessErrorCodes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final MemberRepository memberRepository;
    private final BankClient bankClient;

    @Override
    public List<FindAccountFeignResponse> findMyAccounts(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return bankClient.findMyAccounts(member.getBankMemberId());
    }

    @Override
    @Transactional
    public void registerMainAccount(Long memberId, RegisterMainAccountRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));

        VerifyAccountFeignRequest verifyAccountFeignRequest =
                VerifyAccountFeignRequest.from(member.getBankMemberId(), request);
        VerifyAccountResponse response = bankClient.verifyAccount(verifyAccountFeignRequest);

        member.registerAccount(response.getAccountNo());
    }

    @Override
    public AccountInfoResponse findMyMainAccountInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
        if (member.getAccountNo() == null) {
            throw new BusinessException(BusinessErrorCodes.MAIN_ACCOUNT_NOT_SET);
        }
        return bankClient.findAccountsInfo(member.getAccountNo());
    }

    @Override
    public void verifyAccountPassword(VerifyAccountPasswordRequest request) {
        bankClient.verifyAccountPassword(request);
    }

}
