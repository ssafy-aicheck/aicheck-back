package com.aicheck.bank.domain.account.application;

import com.aicheck.bank.domain.account.dto.FindAccountsFeignResponse;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignRequest;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignResponse;
import java.util.List;

public interface AccountService {
    List<FindAccountsFeignResponse> findMyAccountsByMemberId(Long memberId);

    VerifyAccountFeignResponse verifyAccount(VerifyAccountFeignRequest request);
}
