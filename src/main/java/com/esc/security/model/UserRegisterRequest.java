package com.esc.security.model;

import jakarta.validation.constraints.Pattern;

public record UserRegisterRequest(
        String name,

        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password must be at least 8 characters and contain at least one uppercase letter, one lowercase letter, and one special character"
        )
        String password,

        String email,

        @Pattern(
                regexp = "ROLE_USER|ROLE_ADMIN",
                message = "Role must be either ROLE_USER or ROLE_ADMIN"
        )
        String role
) {
}