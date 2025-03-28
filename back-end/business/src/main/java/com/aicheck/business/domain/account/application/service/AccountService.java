package com.aicheck.business.domain.account.application.service;

import com.aicheck.business.domain.account.dto.AccountInfoResponse;
import com.aicheck.business.domain.account.dto.ChildAccountInfoResponse;
import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import com.aicheck.business.domain.account.dto.VerifyAccountPasswordRequest;
import com.aicheck.business.domain.account.dto.RegisterMainAccountRequest;
import java.util.List;

public interface AccountService {
    List<FindAccountFeignResponse> findMyAccounts(Long memberId);

    void registerMainAccount(Long memberId, RegisterMainAccountRequest request);

    AccountInfoResponse findMyMainAccountInfo(Long memberId);

    void verifyAccountPassword(VerifyAccountPasswordRequest request);

    List<ChildAccountInfoResponse> findMyChildAccounts(Long memberId);
}
