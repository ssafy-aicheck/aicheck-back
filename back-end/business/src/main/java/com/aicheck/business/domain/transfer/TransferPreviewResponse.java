package com.aicheck.business.domain.transfer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferPreviewResponse {
    private TransferReceiverDto receiver;
    private TransferSenderDto sender;
}
