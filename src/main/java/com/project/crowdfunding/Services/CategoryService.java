package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Category;
import com.project.crowdfunding.dto.request.CategoryRequestDto;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequestDto category);
    List<Category> getAllCategories();

    void deleteCategoryById(Long id);
}

