package com.project.crowdfunding.Enums;

public enum UserRoles {
    ROLE_ADMIN,
    ROLE_USER;


    public static UserRoles fromString(String role) {
        return switch (role.toLowerCase()) {
            case "user" -> ROLE_USER;
            case "admin" -> ROLE_ADMIN;
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
