package com.aicheck.business.domain.allowance.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.business.domain.allowance.application.service.AllowanceService;
import com.aicheck.business.domain.allowance.presentation.dto.request.SaveAllowanceRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class AllowanceChatController {

	private final AllowanceService allowanceService;

	// @PostMapping("/allowance")
	// ResponseEntity<Long> saveAllowanceRequest(@RequestBody SaveAllowanceRequest request){
	//
	// }
}
