package br.com.buildrun.springsecurity.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank(message = "Username is required")
        String username,

        @Size(min = 6, message = "Password must be at least 6 characteres long")
        String password
) {}
