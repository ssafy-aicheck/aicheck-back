package com.aicheck.business.domain.allowance.application.service;

import static com.aicheck.business.domain.auth.domain.entity.MemberType.*;
import static com.aicheck.business.global.error.BusinessErrorCodes.BUSINESS_MEMBER_NOT_FOUND;
import static com.aicheck.business.global.error.BusinessErrorCodes.NOT_FOUND_ALLOWANCE_REQUEST;
import static com.aicheck.business.global.error.BusinessErrorCodes.UNAUTHORIZED_UPDATE_ALLOWANCE_REQUEST_STATUS;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.business.domain.allowance.entity.AllowanceRequest;
import com.aicheck.business.domain.allowance.presentation.dto.request.SaveAllowanceRequest;
import com.aicheck.business.domain.allowance.presentation.dto.request.UpdateAllowanceRequestResponse;
import com.aicheck.business.domain.allowance.presentation.dto.response.AllowanceResponse;
import com.aicheck.business.domain.allowance.repository.AllowanceRequestRepository;
import com.aicheck.business.domain.auth.domain.entity.MemberType;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.auth.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AllowanceServiceImpl implements AllowanceService {

	private final AllowanceRequestRepository allowanceRequestRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public Long saveAllowanceRequest(final SaveAllowanceRequest request) {
		final AllowanceRequest allowanceRequest = AllowanceRequest.builder()
			.parentId(request.parentId())
			.childId(request.childId())
			.amount(request.amount())
			.firstCategoryName(request.firstCategoryName())
			.secondCategoryName(request.secondCategoryName())
			.description(request.description())
			.build();

		allowanceRequestRepository.save(allowanceRequest);
		return allowanceRequest.getId();
	}

	@Override
	public List<AllowanceResponse> getAllowanceRequests(Long memberId) {
		final MemberType memberType = memberRepository.findMemberTypeById(memberId)
			.orElseThrow(() -> new BusinessException(BUSINESS_MEMBER_NOT_FOUND));

		if(memberType.equals(PARENT)){
			allowanceRequestRepository.findAllByParentId(memberId);
		}else{
			allowanceRequestRepository.findAllByChildId(memberId);
		}
		return List.of();
	}

	@Override
	public void updateAllowanceRequestResponse(Long parentId, UpdateAllowanceRequestResponse updateAllowanceRequestResponse) {
		final AllowanceRequest allowanceRequest = allowanceRequestRepository.findById(updateAllowanceRequestResponse.id())
			.orElseThrow(() -> new BusinessException(NOT_FOUND_ALLOWANCE_REQUEST));

		if(!parentId.equals(allowanceRequest.getParentId())){
			throw new BusinessException(UNAUTHORIZED_UPDATE_ALLOWANCE_REQUEST_STATUS);
		}

		allowanceRequest.updateStatus(updateAllowanceRequestResponse.status());
	}

	@Override
	public AllowanceResponse getAllowanceRequest(Long id) {
		return null;
	}
}
