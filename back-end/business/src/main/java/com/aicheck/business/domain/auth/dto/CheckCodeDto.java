package com.aicheck.business.domain.auth.dto;

import lombok.Getter;

@Getter
public class CheckCodeDto {
    private String email;
    private String code;
}
