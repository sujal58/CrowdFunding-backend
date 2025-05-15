package com.project.crowdfunding.Enums;

public enum CampaignStatus {

    ACTIVE,
    COMPLETED,
    CANCELLED,
    SUSPICIOUS;

    public static CampaignStatus fromString(String status) {
        return switch (status.toLowerCase()) {
            case "active" -> ACTIVE;
            case "completed" -> COMPLETED;
            case "cancelled" -> CANCELLED;
            case "suspicious" -> SUSPICIOUS;
            default -> throw new IllegalArgumentException("Invalid KYC status: " + status);
        };
    }
}
