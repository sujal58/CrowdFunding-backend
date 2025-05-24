package com.project.crowdfunding.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse {
    private String message;
    private Object data;
    private String path;
    private LocalDateTime timestamp;

    // Constructor for successful response with data
    public ApiResponse(String message, Object data, String path) {
        this.message = message;
        this.data = data;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }


    // Constructor for successful response without data
    public ApiResponse(String message) {
        this.message = message;
    }

    // Constructor for failure response with custom message and Error message
    public ApiResponse(String message, String error, String path) {
        this.message = message;
        this.data = error;
        this.path = path;//Assuming error is a String message
        this.timestamp = LocalDateTime.now();
    }

    // Static helper method for a successful response with data and path
    public static ApiResponse success(String message, Object data, String path) {
        return new ApiResponse(message, data, path);
    }


    // Static helper method for a successful response without data
    public static ApiResponse success(String message) {
        return new ApiResponse( message);
    }

    // Static helper method for an error response
    public static ApiResponse error(String message, String error, String path) {
        return new ApiResponse(message, error, path);
    }

}

