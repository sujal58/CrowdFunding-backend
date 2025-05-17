package com.project.crowdfunding.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagRequestDto {

    @NotBlank(message = "Tag name is required.")
    private String name;
}
