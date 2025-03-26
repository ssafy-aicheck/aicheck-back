package com.aicheck.business.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SignupRequest {
    private String email;
    private String password;
    @JsonProperty("is_parent")
    private boolean isParent;
}
