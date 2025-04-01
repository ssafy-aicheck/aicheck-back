package com.aicheck.business.domain.member.application;

import com.aicheck.business.domain.auth.domain.entity.Member;
import com.aicheck.business.domain.auth.domain.entity.MemberType;
import com.aicheck.business.domain.auth.domain.repository.MemberRepository;
import com.aicheck.business.domain.member.presentation.dto.ChildrenProfileResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public List<ChildrenProfileResponse> getChildrenProfile(Long memberId) {
        List<Member> children = memberRepository.findMembersByManagerIdAndType(memberId, MemberType.CHILD);
        return children.stream().map(ChildrenProfileResponse::from).toList();
    }
}
