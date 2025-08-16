package com.project.crowdfunding.Enums;

public enum CampaignStatus {

    PENDING,
    ACTIVE,
    COMPLETED,
    CANCELLED,
    SUSPICIOUS;

    public static CampaignStatus fromString(String status) {
        return switch (status.toLowerCase()) {
            case "pending" -> PENDING;
            case "active" -> ACTIVE;
            case "completed" -> COMPLETED;
            case "cancelled" -> CANCELLED;
            case "suspicious" -> SUSPICIOUS;
            default -> throw new IllegalArgumentException("Invalid KYC status: " + status);
        };
    }
}
