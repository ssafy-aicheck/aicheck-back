package com.aicheck.bank.domain.account.application;

import com.aicheck.bank.domain.account.dto.AccountInfoFeignResponse;
import com.aicheck.bank.domain.account.dto.FindAccountsFeignResponse;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignRequest;
import com.aicheck.bank.domain.account.dto.VerifyAccountFeignResponse;
import com.aicheck.bank.domain.account.dto.VerifyAccountPasswordFeignRequest;
import com.aicheck.bank.domain.account.dto.VerifyAccountPasswordFeignResponse;
import java.util.List;

public interface AccountService {
    List<FindAccountsFeignResponse> findMyAccountsByMemberId(Long memberId);

    VerifyAccountPasswordFeignResponse verifyAccountPassword(VerifyAccountPasswordFeignRequest request);

    AccountInfoFeignResponse getAccountInfoByNumber(String accountNo);

    VerifyAccountFeignResponse verifyAccount(VerifyAccountFeignRequest request);

    List<AccountInfoFeignResponse> findChildrenAccountInfos(List<String> accountNos);
}
