package com.aicheck.chatbot.infrastructure.client.business;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.aicheck.chatbot.domain.chat.Interval;
import com.aicheck.chatbot.infrastructure.client.business.dto.request.SaveAllowanceRequest;
import com.aicheck.chatbot.infrastructure.client.business.dto.response.TransactionInfoResponse;

@FeignClient(name = "business")
public interface BusinessFeignClient {

	@GetMapping("chatbot/transaction_records")
	TransactionInfoResponse getTransactionInfo(
		@RequestParam Long childId, @RequestParam LocalDate startDate, @RequestParam Interval interval);

	@PostMapping("chatbot/allowance")
	Long saveAllowanceRequest(@RequestBody SaveAllowanceRequest request);
}
