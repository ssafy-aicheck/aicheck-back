package com.aicheck.business.domain.transfer.presentation;

import com.aicheck.business.domain.transfer.application.TransferService;
import com.aicheck.business.domain.transfer.presentation.dto.TransferPreviewResponse;
import com.aicheck.business.domain.transfer.presentation.dto.TransferRequest;
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
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    @GetMapping("/{accountNo}")
    public ResponseEntity<TransferPreviewResponse> getTransferPreview(@CurrentMemberId Long memberId,
                                                                      @PathVariable String accountNo) {
        TransferPreviewResponse response = transferService.getTransferPreview(memberId, accountNo);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> transfer(@CurrentMemberId Long memberId,
                                      @RequestBody TransferRequest transferRequest) {
        transferService.executeTransfer(memberId, transferRequest);
        return null;
    }

}
