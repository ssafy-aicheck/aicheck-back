package com.aicheck.business.domain.transfer;

import com.aicheck.business.domain.account.infrastructure.client.BankClient;
import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.global.error.BusinessErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final MemberRepository memberRepository;
    private final BankClient bankClient;

    @Override
    public TransferPreviewResponse getTransferPreview(Long memberId, String accountNo) {
        TransferReceiverDto receiverDto = bankClient.findReceiverAccountInfo(accountNo);
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
        receiverDto.setImage(member.getProfileUrl());

        TransferSenderDto senderDto = bankClient.findSenderAccountInfo(member.getAccountNo());

        return TransferPreviewResponse.builder()
                .receiver(receiverDto)
                .sender(senderDto)
                .build();
    }
}
