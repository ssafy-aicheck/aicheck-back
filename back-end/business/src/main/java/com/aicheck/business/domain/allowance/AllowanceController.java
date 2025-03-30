package com.aicheck.business.domain.allowance;

import com.aicheck.business.global.auth.annotation.CurrentMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/allowance")
public class AllowanceController {

    private final AllowanceService allowanceService;

    @PostMapping("/increase")
    public ResponseEntity<Void> requestAllowanceIncrease(@CurrentMemberId Long childId,
                                                         @RequestBody CreateAllowanceIncreaseRequest request) {
        allowanceService.requestIncrease(childId, request);
        return ResponseEntity.ok().build();
    }

}
