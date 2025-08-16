package com.project.crowdfunding.controller;

import com.project.crowdfunding.Services.TagServiceImpl;
import com.project.crowdfunding.dto.request.TagRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@Tag(name = "Tags", description = "Endpoints for creating, fetching, and deleting tags")
public class TagController {

    private final TagServiceImpl tagService;
    private final HttpServletRequest servletRequest;

    @Operation(
            summary = "Create Tag",
            description = "Creates a new tag for campaigns or content classification."
    )
    @PostMapping
    public ResponseEntity<ApiResponse> createTags(@Valid @RequestBody TagRequestDto request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Tag created successfully!",
                        tagService.createTag(request),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get All Tags",
            description = "Fetches all existing tags."
    )
    @GetMapping
    public ResponseEntity<ApiResponse> getAllTags() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All tags fetched successfully!",
                        tagService.getAllTags(),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Delete Tag",
            description = "Deletes a tag by its ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Tag deleted successfully!"
                )
        );
    }
}
