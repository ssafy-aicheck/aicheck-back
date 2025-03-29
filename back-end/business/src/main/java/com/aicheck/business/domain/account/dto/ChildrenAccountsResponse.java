package com.aicheck.business.domain.account.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenAccountsResponse {
    private List<ChildAccountInfoResponse> accounts;
}
