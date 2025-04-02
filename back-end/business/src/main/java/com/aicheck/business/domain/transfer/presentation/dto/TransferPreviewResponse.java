package com.aicheck.business.domain.transfer.presentation.dto;

import com.aicheck.business.domain.transfer.dto.TransferReceiverDto;
import com.aicheck.business.domain.transfer.dto.TransferSenderDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferPreviewResponse {
    private TransferReceiverDto receiver;
    private TransferSenderDto sender;
}
