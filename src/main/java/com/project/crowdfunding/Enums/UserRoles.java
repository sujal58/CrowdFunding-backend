package com.project.crowdfunding.Enums;

public enum UserRoles {
    ROLE_ADMIN,
    ROLE_CREATOR,
    ROLE_DONOR;


    public static UserRoles fromString(String role) {
        return switch (role.toLowerCase()) {
            case "creator" -> ROLE_CREATOR;
            case "donor" -> ROLE_DONOR;
            case "admin" -> ROLE_ADMIN;
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
