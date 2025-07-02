package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.CategoryService;
import com.project.crowdfunding.dto.request.CategoryRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Endpoints for managing campaign categories (create, fetch, delete)")
public class CategoryController {

    private final CategoryService categoryService;
    private final HttpServletRequest servletRequest;

    @Operation(
            summary = "Create Category",
            description = "Creates a new campaign category. Typically used by admin users to organize campaigns."
    )
    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryRequestDto request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Category created successfully!",
                        categoryService.createCategory(request),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get All Categories",
            description = "Retrieves a list of all available campaign categories."
    )
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategory() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Categories fetched successfully!",
                        categoryService.getAllCategories(),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Delete Category",
            description = "Deletes a campaign category by ID. Intended for administrative use."
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Category deleted successfully!"
                )
        );
    }
}
