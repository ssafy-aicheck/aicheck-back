package com.aicheck.business.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @JsonProperty("is_parent")
    private Boolean isParent;
}
