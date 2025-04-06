package com.aicheck.business.domain.member.application;

import com.aicheck.business.domain.member.dto.MemberInfoResponse;
import com.aicheck.business.domain.member.presentation.dto.ChildrenProfileResponse;
import com.aicheck.business.domain.member.presentation.dto.ProfileResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    List<ChildrenProfileResponse> getChildrenProfile(Long memberId);

    ProfileResponse getMyProfile(Long memberId);

    MemberInfoResponse getMemberInfo(Long memberId);

    void updateMemberInfo(Long memberId, MultipartFile image);
}
