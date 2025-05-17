package com.project.crowdfunding.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CategoryRequestDto {

    @NotBlank(message = "Category name is required.")
    private String name;
}
