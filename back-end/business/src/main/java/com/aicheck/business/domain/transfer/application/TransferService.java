package com.aicheck.business.domain.transfer.application;

import com.aicheck.business.domain.transfer.presentation.dto.TransferPreviewResponse;

public interface TransferService {
    TransferPreviewResponse getTransferPreview(Long memberId, String accountNo);
}
