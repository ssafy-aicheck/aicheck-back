package com.aicheck.chatbot.infrastructure.client.business;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aicheck.chatbot.infrastructure.client.business.dto.response.MemberInfo;

@FeignClient(name = "batch")
public interface BatchFeignClient {

	@GetMapping("chatbot/{memberId}")
	MemberInfo getMemberInfo(@PathVariable Long memberId);
}