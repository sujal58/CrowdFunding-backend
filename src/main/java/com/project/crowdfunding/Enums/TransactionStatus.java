package com.project.crowdfunding.Enums;

public enum TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED;


    public static TransactionStatus fromString(String status) {
        return switch (status.toLowerCase()) {
            case "active" -> PENDING;
            case "completed" -> COMPLETED;
            case "cancelled" -> FAILED;
            default -> throw new IllegalArgumentException("Invalid Transaction status: " + status);
        };
    }


}
