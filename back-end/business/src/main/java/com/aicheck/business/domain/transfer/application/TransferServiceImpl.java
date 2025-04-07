package com.aicheck.business.domain.transfer.application;

import static com.aicheck.business.global.infrastructure.event.Type.TRANSFER;
import static com.aicheck.business.global.infrastructure.event.Type.TRANSFER_FAILED;

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
import com.aicheck.business.global.infrastructure.event.AlarmEventProducer;
import com.aicheck.business.global.infrastructure.event.dto.request.AlarmEventMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransferServiceImpl implements TransferService {

	private final MemberRepository memberRepository;
	private final BankClient bankClient;
	private final TransactionRecordService transactionRecordService;
	private final AlarmEventProducer alarmEventProducer;

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
	@Transactional
	public void executeTransfer(Long memberId, TransferRequest transferRequest) {
		Member sender = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND));
		String fromAccountNo = sender.getAccountNo();

		Member receiver = memberRepository.findMemberByAccountNo(transferRequest.getReceiverAccountNo());

		TransferExecuteRequest request = TransferExecuteRequest.builder()
			.fromAccountNo(fromAccountNo)
			.toAccountNo(transferRequest.getReceiverAccountNo())
			.amount(transferRequest.getAmount())
			.build();

		try {
			bankClient.executeTransfer(request);
		} catch (Exception e) {
			alarmEventProducer.sendEvent(
				AlarmEventMessage.of(
					sender.getId(),
					getFailTitle(receiver.getName()),
					getFailBody(receiver.getName(), transferRequest.getAmount()),
					TRANSFER_FAILED,
					null));
			throw e;
		}

		Long senderEndPoint = transactionRecordService.saveWithdrawTransaction(
			sender.getId(), receiver.getName(), transferRequest.getAmount());
		Long receiverEndPoint = transactionRecordService.saveDepositTransaction(
			receiver.getId(), sender.getName(), transferRequest.getAmount());

		alarmEventProducer.sendEvent(
			AlarmEventMessage.of(
				sender.getId(),
				getSenderSuccessTitle(receiver.getName()),
				getSenderSuccessBody(receiver.getName(), transferRequest.getAmount()),
				TRANSFER,
				senderEndPoint
			)
		);

		alarmEventProducer.sendEvent(
			AlarmEventMessage.of(
				receiver.getId(),
				getReceiverSuccessTitle(sender.getName()),
				getReceiverSuccessBody(sender.getName(), transferRequest.getAmount()),
				TRANSFER,
				receiverEndPoint
			)
		);
	}

	private String getFailTitle(String name) {
		return String.format("%s님에게 송금이 실패했어요", name);
	}

	private String getFailBody(String name, Long amount) {
		return String.format("잔액 부족으로 %s님에게 %,d원을 송금하지 못했어요.", name, amount);
	}

	private String getSenderSuccessTitle(String receiverName) {
		return String.format("%s님에게 송금이 완료됐어요", receiverName);
	}

	private String getSenderSuccessBody(String receiverName, Long amount) {
		return String.format("%s님에게 %,d원을 성공적으로 송금했어요.", receiverName, amount);
	}

	private String getReceiverSuccessTitle(String senderName) {
		return String.format("%s님으로부터 송금이 도착했어요", senderName);
	}

	private String getReceiverSuccessBody(String senderName, Long amount) {
		return String.format("%s님이 %,d원을 송금했어요. 입금이 완료됐어요!", senderName, amount);
	}
}
