package com.esc.security.dto;

import java.util.List;
import java.util.UUID;

public record UserModelDTO(
        UUID id,
        String name,
        String email,
        List<String> roles) {
}