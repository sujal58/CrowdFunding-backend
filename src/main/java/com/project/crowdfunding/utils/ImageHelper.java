package com.project.crowdfunding.utils;


import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ImageHelper {
    public static String buildImageUrl(String imagePath) {
        if (imagePath == null) return null;

        String normalizedPath = imagePath.replace("\\", "/");

        if (normalizedPath.startsWith("uploads/")) {
            normalizedPath = normalizedPath.substring("uploads/".length());
        }

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(normalizedPath)
                .toUriString();
    }
}

