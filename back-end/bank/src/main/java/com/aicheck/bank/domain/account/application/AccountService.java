package com.aicheck.bank.domain.account.application;

import com.aicheck.bank.domain.account.dto.FindAccountsFeignResponse;
import java.util.List;

public interface AccountService {
    List<FindAccountsFeignResponse> findMyAccountsByMemberId(Long memberId);
}
