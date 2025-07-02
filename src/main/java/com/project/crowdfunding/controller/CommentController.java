package com.project.crowdfunding.controller;

import com.project.crowdfunding.Services.CommentServiceImpl;
import com.project.crowdfunding.dto.request.CommentRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "Endpoints for managing comments on crowdfunding campaigns")
public class CommentController {

    private final CommentServiceImpl commentService;
    private final HttpServletRequest servletRequest;

    @Operation(
            summary = "Create Comment",
            description = "Allows a user to add a comment to a specific campaign using the campaign's ID."
    )
    @PostMapping("/{campaignId}")
    public ResponseEntity<ApiResponse> createComment(
            @PathVariable Long campaignId,
            @Valid @RequestBody CommentRequestDto request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Comment created successfully!",
                        commentService.createComment(campaignId, request),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get All Comments",
            description = "Retrieves a list of all comments across all campaigns. Useful for moderation or admin views."
    )
    @GetMapping
    public ResponseEntity<ApiResponse> getAllComment() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Comments fetched successfully!",
                        commentService.getAllComments(),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get Comments by Campaign",
            description = "Fetches all comments related to a specific campaign using its campaign ID."
    )
    @GetMapping("/{campaignId}")
    public ResponseEntity<ApiResponse> getCommentByCampaignId(@PathVariable Long campaignId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Comments fetched successfully!",
                        commentService.getCommentsByCampaignId(campaignId),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Delete Comment",
            description = "Deletes a specific comment using its ID. Only accessible to admins or comment owners."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Comment deleted successfully!"
                )
        );
    }
}
