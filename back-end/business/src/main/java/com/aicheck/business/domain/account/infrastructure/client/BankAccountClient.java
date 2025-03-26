package com.aicheck.business.domain.account.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "bank")
public interface BankAccountClient {


}
