package com.aicheck.bank.domain.member.presentation;

import com.aicheck.bank.domain.member.application.MemberService;
import com.aicheck.bank.domain.member.dto.FindBankMemberFeignResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{email}")
    public ResponseEntity<FindBankMemberFeignResponse> findBankMember(@PathVariable String email) {
        return ResponseEntity.ok(memberService.findBankMemberByEmail(email));
    }

}
