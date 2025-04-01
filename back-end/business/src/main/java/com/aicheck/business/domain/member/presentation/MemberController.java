package com.aicheck.business.domain.member.presentation;

import com.aicheck.business.domain.member.presentation.dto.ChildrenProfileResponse;
import com.aicheck.business.domain.member.application.MemberService;
import com.aicheck.business.domain.member.presentation.dto.ProfileResponse;
import com.aicheck.business.global.auth.annotation.CurrentMemberId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/details")
    public ResponseEntity<ProfileResponse> getProfile(@CurrentMemberId Long memberId) {
        return ResponseEntity.ok(memberService.getMyProfile(memberId));
    }

    @GetMapping("/children/profiles")
    public ResponseEntity<List<ChildrenProfileResponse>> getChildrenProfiles(@CurrentMemberId Long memberId) {
        return ResponseEntity.ok(memberService.getChildrenProfile(memberId));
    }

}
