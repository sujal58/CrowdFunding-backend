package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Category;
import com.project.crowdfunding.Repository.CategoryRepository;
import com.project.crowdfunding.dto.request.CategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public Category createCategory(CategoryRequestDto dto) {
        if(dto == null){
            throw new RuntimeException("Category cannot be empty!");
        }
        Category category = modelMapper.map(dto, Category.class);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found with id: "+ id));
        categoryRepository.delete(category);
    }
}

