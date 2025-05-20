package com.project.crowdfunding.dto.response;

import com.project.crowdfunding.Entity.Role;
import lombok.Data;

import java.util.Set;


@Data
public class SignUpResponseDto {

    private Long userId;
    private String email;
    private String userName;
    private Set<Role> roles;


    public SignUpResponseDto(Long userId, String email, String userName, Set<Role> roles) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.roles = roles;
    }
}
