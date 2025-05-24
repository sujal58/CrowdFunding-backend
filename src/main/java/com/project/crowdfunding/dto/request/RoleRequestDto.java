package com.project.crowdfunding.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleRequestDto {

    @NotNull(message = "Role name cannot be null!")
    private String roleName;
}
