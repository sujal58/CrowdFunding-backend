package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategories();
}

