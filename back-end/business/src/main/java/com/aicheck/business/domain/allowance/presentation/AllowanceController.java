package com.aicheck.business.domain.allowance.presentation;

import com.aicheck.business.domain.allowance.application.service.AllowanceService;
import com.aicheck.business.domain.allowance.dto.AllowanceIncreaseDecisionRequest;
import com.aicheck.business.domain.allowance.dto.AllowanceIncreaseRequestDetailResponse;
import com.aicheck.business.domain.allowance.dto.CreateAllowanceIncreaseRequest;
import com.aicheck.business.global.auth.annotation.CurrentMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/allowance")
public class AllowanceController {

    private final AllowanceService allowanceService;

    // 인상 요청
    @PostMapping("/increase")
    public ResponseEntity<Void> requestAllowanceIncrease(@CurrentMemberId Long childId,
                                                         @RequestBody CreateAllowanceIncreaseRequest request) {
        allowanceService.requestIncrease(childId, request);
        return ResponseEntity.ok().build();
    }

    // 인상 요청 응답
    @PostMapping("/increase/{requestId}")
    public ResponseEntity<Void> respondAllowanceIncrease(@PathVariable Long requestId,
                                                         @RequestBody AllowanceIncreaseDecisionRequest request) {
        allowanceService.respondToRequest(requestId, request);
        return ResponseEntity.ok().build();
    }

    // 인상 요청 디테일
    @GetMapping("/increase/details/{id}")
    public ResponseEntity<AllowanceIncreaseRequestDetailResponse> findAllowanceIncreaseRequestDetails(
            @PathVariable Long id) {
        return ResponseEntity.ok(allowanceService.getAllowanceIncreaseRequestDetail(id));
    }

}
