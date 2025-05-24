package com.project.crowdfunding.controller;

import com.project.crowdfunding.Services.CommentServiceImpl;
import com.project.crowdfunding.dto.request.CommentRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    private final HttpServletRequest servletRequest;

    @PostMapping("/{campaignId}")
    public ResponseEntity<ApiResponse> createComment(@PathVariable Long campaignId, @RequestBody CommentRequestDto request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Comment created successfully!",
                        commentService.createComment(campaignId, request),
                        servletRequest.getRequestURI()
                )
        );

    }

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


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Comment deleted successfully!"
                )
        );
    }



    //    @PutMapping("/{id}")
//    public ResponseEntity<Comment> update(@PathVariable Long id, @RequestBody CommentRequestDto request) {
//        return ResponseEntity.ok(commentService.(id, request));
//    }

}
