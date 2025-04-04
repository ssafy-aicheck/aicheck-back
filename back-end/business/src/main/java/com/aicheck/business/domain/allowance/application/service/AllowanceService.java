package com.aicheck.business.domain.allowance.application.service;

import java.util.List;

import com.aicheck.business.domain.allowance.presentation.dto.request.SaveAllowanceRequest;
import com.aicheck.business.domain.allowance.presentation.dto.request.UpdateAllowanceRequestResponse;
import com.aicheck.business.domain.allowance.presentation.dto.response.AllowanceResponse;

public interface AllowanceService {
	Long saveAllowanceRequest(SaveAllowanceRequest request);
	List<AllowanceResponse> getAllowanceRequests(Long memberId);
	void updateAllowanceRequestResponse(Long parentId, UpdateAllowanceRequestResponse updateAllowanceRequestResponse);
	AllowanceResponse getAllowanceRequest(Long id);
}
