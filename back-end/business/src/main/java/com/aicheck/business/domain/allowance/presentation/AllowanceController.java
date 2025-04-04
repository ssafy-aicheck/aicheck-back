package com.aicheck.business.domain.allowance.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.business.domain.allowance.application.service.AllowanceService;
import com.aicheck.business.domain.allowance.presentation.dto.request.UpdateAllowanceRequestResponse;
import com.aicheck.business.domain.allowance.presentation.dto.response.AllowanceResponse;
import com.aicheck.business.global.auth.annotation.CurrentMemberId;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/allowance")
public class AllowanceController {

	private final AllowanceService allowanceService;

	@GetMapping
	public ResponseEntity<List<AllowanceResponse>> getAllowanceRequests(@CurrentMemberId Long memberId){
		return ResponseEntity.ok(allowanceService.getAllowanceRequests(memberId));
	}

	@PatchMapping
	public ResponseEntity<Void> updateAllowanceRequestResponse(
		@CurrentMemberId Long parentId,
		@Valid @RequestBody UpdateAllowanceRequestResponse updateAllowanceRequestResponse){
		allowanceService.updateAllowanceRequestResponse(parentId, updateAllowanceRequestResponse);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/detail/{id}")
	public ResponseEntity<AllowanceResponse> getAllowanceRequest(@PathVariable Long id){
		return ResponseEntity.ok(allowanceService.getAllowanceRequest(id));
	}
}
