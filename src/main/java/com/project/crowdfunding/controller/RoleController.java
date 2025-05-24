package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.RoleService;
import com.project.crowdfunding.dto.request.RoleRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private HttpServletRequest servletRequest;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRoles(){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All Roles fetched successfully!",
                        roleService.getAllRoles(),
                        servletRequest.getRequestURI()
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createRoles(@Valid @RequestBody RoleRequestDto request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Role created successfully!",
                        roleService.createRole(request),
                        servletRequest.getRequestURI()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Role deleted successfully!"
                )
        );
    }
}
