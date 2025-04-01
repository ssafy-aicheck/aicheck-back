package com.aicheck.business.domain.transfer;

public interface TransferService {
    TransferPreviewResponse getTransferPreview(Long memberId, String accountNo);
}
