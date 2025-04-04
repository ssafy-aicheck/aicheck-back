package com.aicheck.business.domain.transfer.application;

import com.aicheck.business.domain.transfer.presentation.dto.TransferPreviewResponse;
import com.aicheck.business.domain.transfer.presentation.dto.TransferRequest;

public interface TransferService {
    TransferPreviewResponse getTransferPreview(Long memberId, String accountNo);

    void executeTransfer(Long memberId, TransferRequest transferRequest);
}
