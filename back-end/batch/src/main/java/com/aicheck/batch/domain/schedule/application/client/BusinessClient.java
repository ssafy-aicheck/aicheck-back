package com.aicheck.batch.domain.schedule.application.client;

import com.aicheck.batch.domain.report.application.dto.MemberInfoResponse;
import com.aicheck.batch.domain.report.dto.MemberTransactionRecords;
import com.aicheck.batch.domain.schedule.application.client.dto.ChildAccountInfoResponse;
import com.aicheck.batch.domain.schedule.dto.AccountNoResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "business")
public interface BusinessClient {

    @GetMapping("/accounts/children/internal/{memberId}")
    List<ChildAccountInfoResponse> getChildrenAccounts(@PathVariable("memberId") Long memberId);

    @GetMapping("/accounts/number/{memberId}")
    AccountNoResponse getAccountNumber(@PathVariable("memberId") Long memberId);

    @GetMapping("/transaction-records/statistics")
    List<MemberTransactionRecords> getChildrenTransactions();

    @GetMapping("/members/internal/info/{memberId}")
    MemberInfoResponse getMemberInfo(@PathVariable("memberId") Long memberId);

}
