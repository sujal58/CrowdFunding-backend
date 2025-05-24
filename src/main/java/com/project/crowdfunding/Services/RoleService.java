package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Role;
import com.project.crowdfunding.dto.request.RoleRequestDto;
import com.project.crowdfunding.dto.response.UserResponseDto;

import java.util.List;

public interface RoleService {
        Role createRole(RoleRequestDto roleDto);
        List<Role> getAllRoles();
        void deleteRole(Long id);
        UserResponseDto assignRole(String username,String role);
}
