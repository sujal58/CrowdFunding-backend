package com.project.crowdfunding.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDto {

//    @NotNull(message = "User ID is required.")
    private Long userId;

//    @NotNull(message = "Campaign ID is required.")
    private Long campaignId;

    @NotBlank(message = "Comment content cannot be empty.")
    @Size(max = 500, message = "Comment cannot exceed 500 characters.")
    private String content;
}
