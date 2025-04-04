package com.aicheck.business.domain.transfer.application;

import com.aicheck.business.domain.account.infrastructure.client.BankClient;
import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.transaction_record.application.TransactionRecordService;
import com.aicheck.business.domain.transfer.dto.TransferReceiverDto;
import com.aicheck.business.domain.transfer.dto.TransferSenderDto;
import com.aicheck.business.domain.transfer.dto.feign.TransferExecuteRequest;
import com.aicheck.business.domain.transfer.presentation.dto.TransferPreviewResponse;
import com.aicheck.business.domain.transfer.presentation.dto.TransferRequest;
import com.aicheck.business.global.error.BusinessErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final MemberRepository memberRepository;
    private final BankClient bankClient;
    private final TransactionRecordService transactionRecordService;

    @Override
    public TransferPreviewResponse getTransferPreview(Long memberId, String accountNo) {
        TransferReceiverDto receiverDto = bankClient.findReceiverAccountInfo(accountNo);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
        receiverDto.setImage(member.getProfileUrl());

        TransferSenderDto senderDto = bankClient.findSenderAccountInfo(member.getAccountNo());

        return TransferPreviewResponse.builder()
                .receiver(receiverDto)
                .sender(senderDto)
                .build();
    }

    @Override
    public void executeTransfer(Long memberId, TransferRequest transferRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
        String fromAccountNo = member.getAccountNo();

        TransferExecuteRequest request = TransferExecuteRequest.builder()
                .fromAccountNo(fromAccountNo)
                .toAccountNo(transferRequest.getReceiverAccountNo())
                .amount(transferRequest.getAmount())
                .build();

        bankClient.executeTransfer(request);

    }
}
