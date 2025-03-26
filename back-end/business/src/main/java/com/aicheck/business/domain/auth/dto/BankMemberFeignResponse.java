package com.aicheck.business.domain.auth.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BankMemberFeignResponse {
    private Long id;
    private String email;
    private LocalDate birth;
    private String name;
    private String gender;
}
