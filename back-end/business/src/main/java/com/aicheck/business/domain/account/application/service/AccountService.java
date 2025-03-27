package com.aicheck.business.domain.account.application.service;

import com.aicheck.business.domain.account.dto.FindAccountFeignResponse;
import java.util.List;

public interface AccountService {
    List<FindAccountFeignResponse> findMyAccounts(Long memberId);
}
