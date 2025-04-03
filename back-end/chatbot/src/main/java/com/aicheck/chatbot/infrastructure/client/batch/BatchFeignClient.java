package com.aicheck.chatbot.infrastructure.client.batch;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aicheck.chatbot.infrastructure.client.batch.dto.response.ScheduledAllowance;

@FeignClient(name = "batch")
public interface BatchFeignClient {

	@GetMapping("chatbot/{childId}")
	ScheduledAllowance getScheduledAllowance(@PathVariable Long childId);
}