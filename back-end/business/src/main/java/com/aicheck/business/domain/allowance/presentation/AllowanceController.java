package com.aicheck.business.domain.allowance.presentation;

import java.time.YearMonth;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.business.domain.account.dto.DescriptionRatioResponse;
import com.aicheck.business.domain.allowance.application.client.BatchClient;
import com.aicheck.business.domain.allowance.application.client.dto.ReportSummaryResponse;
import com.aicheck.business.domain.allowance.application.service.AllowanceService;
import com.aicheck.business.domain.allowance.dto.AllowanceRequestDto;
import com.aicheck.business.domain.allowance.presentation.dto.request.SummaryResponse;
import com.aicheck.business.domain.allowance.presentation.dto.request.UpdateAllowanceRequestResponse;
import com.aicheck.business.domain.allowance.presentation.dto.response.AllowanceResponse;
import com.aicheck.business.domain.transaction_record.application.TransactionRecordService;
import com.aicheck.business.global.auth.annotation.CurrentMemberId;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/allowance")
public class AllowanceController {

	private final AllowanceService allowanceService;
	private final TransactionRecordService transactionRecordService;
	private final BatchClient batchClient;

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
	public ResponseEntity<AllowanceRequestDto> getAllowanceRequest(@PathVariable Long id){
		return ResponseEntity.ok(allowanceService.getAllowanceRequest(id));
	}

	@GetMapping("/summary")
	public ResponseEntity<SummaryResponse> getAllowanceSummary(
		@RequestParam("childId") Long childId, @RequestParam("reportId") String reportId){
		final ReportSummaryResponse reportSummaryResponse = batchClient.getReportSummaryResponse(reportId);
		final DescriptionRatioResponse descriptionRatio = transactionRecordService.getDescriptionRatio(childId,
			YearMonth.of(reportSummaryResponse.year(), reportSummaryResponse.month()));
		return ResponseEntity.ok(SummaryResponse.from(descriptionRatio, reportSummaryResponse));
	}
}
