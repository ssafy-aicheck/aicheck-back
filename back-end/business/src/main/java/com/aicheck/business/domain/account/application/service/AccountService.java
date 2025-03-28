package com.aicheck.business.domain.account.application.service;

import com.aicheck.business.domain.account.dto.AccountInfoResponse;
import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import com.aicheck.business.domain.account.dto.VerifyAccountRequest;
import java.util.List;

public interface AccountService {
    List<FindAccountFeignResponse> findMyAccounts(Long memberId);

    void registerMainAccount(Long memberId, VerifyAccountRequest request);

    AccountInfoResponse findMyMainAccountInfo(Long memberId);
}
