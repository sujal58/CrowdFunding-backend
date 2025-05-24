package com.project.crowdfunding.controller;

import com.project.crowdfunding.Services.TagServiceImpl;
import com.project.crowdfunding.dto.request.TagRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagServiceImpl tagService;

    private final HttpServletRequest servletRequest;


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
