package com.aicheck.business.domain.auth.application.service;

import com.aicheck.business.domain.auth.application.client.BankMemberClient;
import com.aicheck.business.domain.auth.dto.BankMemberFeignResponse;
import com.aicheck.business.domain.auth.dto.SignupRequest;
import com.aicheck.business.domain.auth.entity.Member;
import com.aicheck.business.domain.auth.entity.MemberType;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.auth.repository.MemberRepository;
import com.aicheck.business.global.BusinessErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final BankMemberClient bankMemberClient;

    @Transactional
    public void signup(SignupRequest request) {
        BankMemberFeignResponse response = bankMemberClient.findBankMemberByEmail(request.getEmail());

        if (response == null) {
            throw new BusinessException(BusinessErrorCodes.BANK_MEMBER_NOT_FOUND);
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .managerId(response.getId())
                .bankMemberId(response.getId())
                .name(response.getName())
                .birth(response.getBirth())
                .type(request.isParent() ? MemberType.PARENT : MemberType.CHILD)
                .build();

        try {
            memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(BusinessErrorCodes.DUPLICATED_SIGNUP);
        }

    }

}
