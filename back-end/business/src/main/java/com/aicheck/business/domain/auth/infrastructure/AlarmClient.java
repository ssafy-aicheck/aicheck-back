package com.aicheck.business.domain.auth.infrastructure;

import com.aicheck.business.domain.auth.infrastructure.dto.SaveFCMTokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "alarm")
public interface AlarmClient {
    @PostMapping("/alarm")
    void saveFcmToken(SaveFCMTokenRequest request);
}
