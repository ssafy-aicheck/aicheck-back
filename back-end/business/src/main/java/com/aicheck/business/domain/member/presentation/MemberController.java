package com.aicheck.business.domain.member.presentation;

import com.aicheck.business.domain.member.application.MemberService;
import com.aicheck.business.domain.member.dto.MemberInfoResponse;
import com.aicheck.business.domain.member.presentation.dto.ChildrenProfileResponse;
import com.aicheck.business.domain.member.presentation.dto.ProfileResponse;
import com.aicheck.business.global.auth.annotation.CurrentMemberId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/details")
    public ResponseEntity<ProfileResponse> getProfile(@CurrentMemberId Long memberId) {
        return ResponseEntity.ok(memberService.getMyProfile(memberId));
    }

    @PatchMapping("/details")
    public ResponseEntity<Void> updateProfile(@CurrentMemberId Long memberId,
                                              @RequestPart(value = "image", required = false) MultipartFile image) {
        memberService.updateMemberInfo(memberId, image);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/children/profiles")
    public ResponseEntity<List<ChildrenProfileResponse>> getChildrenProfiles(@CurrentMemberId Long memberId) {
        return ResponseEntity.ok(memberService.getChildrenProfile(memberId));
    }

    @GetMapping("/internal/info/{memberId}")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getMemberInfo(memberId));
    }

}
