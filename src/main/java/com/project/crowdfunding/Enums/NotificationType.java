package com.project.crowdfunding.Enums;

public enum NotificationType {
    DONATION,
    COMMENT,
    CAMPAIGN_APPROVED,
    CAMPAIGN_REJECTED,
    KYC_VERIFIED,
    KYC_REJECTED,
    SYSTEM_ALERT;

    public static NotificationType fromString(String type) {
        return switch (type.toLowerCase()) {
            case "donation" -> DONATION;
            case "comment" -> COMMENT;
            case "approved" -> CAMPAIGN_APPROVED;
            case "reject" -> CAMPAIGN_REJECTED;
            case "verified" -> KYC_VERIFIED;
            case "unverified" -> KYC_REJECTED;
            case "system" -> SYSTEM_ALERT;

            default -> throw new IllegalArgumentException("Invalid Notification type: " + type);
        };
    }
}



