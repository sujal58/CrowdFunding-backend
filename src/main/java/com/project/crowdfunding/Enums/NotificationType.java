package com.project.crowdfunding.Enums;

public enum NotificationType {
    SUCCESS,
    INFO,
    ERROR;

    public static NotificationType fromString(String type) {
        return switch (type.toLowerCase()) {
            case "success" -> SUCCESS;
            case "info" -> INFO;
            case "error" -> ERROR;
            default -> throw new IllegalArgumentException("Invalid Notification type: " + type);
        };
    }
}
