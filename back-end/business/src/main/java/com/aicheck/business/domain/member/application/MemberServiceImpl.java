package com.aicheck.business.domain.member.application;

import com.aicheck.business.domain.account.dto.AccountInfoResponse;
import com.aicheck.business.domain.account.infrastructure.client.BankClient;
import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.entity.MemberType;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.member.presentation.dto.ChildrenProfileResponse;
import com.aicheck.business.domain.member.presentation.dto.ProfileResponse;
import com.aicheck.business.global.error.BusinessErrorCodes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BankClient bankClient;

    @Override
    public List<ChildrenProfileResponse> getChildrenProfile(Long memberId) {
        List<Member> children = memberRepository.findMembersByManagerIdAndType(memberId, MemberType.CHILD);
        return children.stream().map(ChildrenProfileResponse::from).toList();
    }

    @Override
    public ProfileResponse getMyProfile(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
        AccountInfoResponse accountInfoResponse = getAccountInfo(member.getAccountNo());
        return ProfileResponse.from(member, accountInfoResponse);
    }

    private AccountInfoResponse getAccountInfo(String accountNo) {
        if(accountNo == null) {
            return null;
        }
        return bankClient.findAccountsInfo(accountNo);
    }

}
