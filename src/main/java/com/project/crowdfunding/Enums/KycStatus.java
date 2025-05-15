package com.project.crowdfunding.Enums;

public enum KycStatus {
    PENDING,
    VERIFIED,
    REJECTED;

    public static KycStatus fromString(String status) {
        return switch (status.toLowerCase()) {
            case "pending" -> PENDING;
            case "verified" -> VERIFIED;
            case "rejected" -> REJECTED;
            default -> throw new IllegalArgumentException("Invalid KYC status: " + status);
        };
    }
}
