package com.aicheck.business.domain.account.application.service;

import static java.time.LocalDate.*;

import com.aicheck.business.domain.account.dto.AccountInfoResponse;
import com.aicheck.business.domain.account.dto.AccountNoResponse;
import com.aicheck.business.domain.account.dto.ChildAccountInfoResponse;
import com.aicheck.business.domain.account.dto.DescriptionRatioResponse;
import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import com.aicheck.business.domain.account.dto.RegisterMainAccountRequest;
import com.aicheck.business.domain.account.dto.VerifyAccountPasswordRequest;
import com.aicheck.business.domain.account.dto.VerifyAccountResponse;
import com.aicheck.business.domain.account.infrastructure.client.BankClient;
import com.aicheck.business.domain.account.infrastructure.client.dto.VerifyAccountFeignRequest;
import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.entity.MemberType;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.transaction_record.repository.TransactionRecordRepository;
import com.aicheck.business.global.error.BusinessErrorCodes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
    public AccountInfoResponse findMyChildAccountInfo(Long memberId, Long childId) {
        Member child = memberRepository.findById(childId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
        if (child.getManagerId() != memberId) {
            throw new BusinessException(BusinessErrorCodes.NOT_YOUR_CHILD);
        }
        return bankClient.findAccountsInfo(child.getAccountNo());
    }

    @Override
    public void verifyAccountPassword(VerifyAccountPasswordRequest request) {
        bankClient.verifyAccountPassword(request);
    }

    @Override
    public List<ChildAccountInfoResponse> findMyChildAccounts(Long memberId) {
        List<Member> children = memberRepository.findMembersByManagerIdAndType(memberId, MemberType.CHILD);
        List<String> childrenAccountNos = children.stream()
                .map(Member::getAccountNo)
                .toList();

        List<AccountInfoResponse> accountFeignResponses = bankClient.findAccountsInfoList(childrenAccountNos);

        Map<String, AccountInfoResponse> accountInfoMap = accountFeignResponses.stream()
                .collect(Collectors.toMap(AccountInfoResponse::getAccountNo, Function.identity()));

        return children.stream()
                .map(child -> {
                    AccountInfoResponse account = accountInfoMap.get(child.getAccountNo());
                    return ChildAccountInfoResponse.builder()
                            .memberId(child.getId())
                            .image(child.getProfileUrl())
                            .name(child.getName())
                            .accountNo(child.getAccountNo() != null ? child.getAccountNo() : null)
                            .accountName(account != null ? account.getAccountName() : null)
                            .balance(account != null ? account.getBalance() : null)
                            .build();
                })
                .toList();
    }

    @Override
    public AccountNoResponse findAccountNoByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
        return AccountNoResponse.builder()
                .accountNo(member.getAccountNo())
                .build();
    }
}
