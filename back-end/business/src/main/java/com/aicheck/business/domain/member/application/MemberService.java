package com.aicheck.business.domain.member.application;

import com.aicheck.business.domain.member.presentation.dto.ChildrenProfileResponse;
import java.util.List;

public interface MemberService {
    List<ChildrenProfileResponse> getChildrenProfile(Long memberId);
}
