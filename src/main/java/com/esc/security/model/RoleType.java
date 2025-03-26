package com.esc.security.model;

public enum RoleType {
    ROLE_USER,
    ROLE_ADMIN;

    // Implement a method to fetch a role by name
    public static RoleType fromString(String role) {
        return RoleType.valueOf(role.toUpperCase());
    }

}
