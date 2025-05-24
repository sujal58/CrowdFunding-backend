package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.CategoryService;
import com.project.crowdfunding.dto.request.CategoryRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private final HttpServletRequest servletRequest;

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryRequestDto request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Category created successfully!",
                        categoryService.createCategory(request),
                        servletRequest.getRequestURI()
                )
        );
    }


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
