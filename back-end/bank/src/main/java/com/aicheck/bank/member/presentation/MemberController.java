package com.aicheck.bank.member.presentation;

import com.aicheck.bank.member.application.MemberServiceImpl;
import com.aicheck.bank.member.dto.FindBankMemberFeignResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    @GetMapping("/{email}")
    public FindBankMemberFeignResponse findBankMember(@PathVariable String email) {
        return memberServiceImpl.findBankMemberByEmail(email);
    }

}
