package com.aicheck.business.domain.transaction_record.presentation;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.business.domain.transaction_record.application.TransactionRecordService;
import com.aicheck.business.domain.transaction_record.presentation.dto.Interval;
import com.aicheck.business.domain.transaction_record.presentation.dto.TransactionInfoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class TransactionRecordChatbotController {

	private final TransactionRecordService transactionRecordService;

	@GetMapping("/transaction_records")
	ResponseEntity<TransactionInfoResponse> getTransactionInfo(
		@RequestParam Long childId, @RequestParam LocalDate startDate, @RequestParam String interval){
		return ResponseEntity.ok(transactionRecordService.getTransactionInfo(childId, startDate, Interval.valueOf(interval)));
	}
}
