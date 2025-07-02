package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.RoleService;
import com.project.crowdfunding.dto.request.RoleRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/roles")
@Tag(name = "Roles", description = "Endpoints for managing user roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private HttpServletRequest servletRequest;

    @Operation(
            summary = "Get All Roles",
            description = "Fetches all user roles available in the system."
    )
    @GetMapping
    public ResponseEntity<ApiResponse> getAllRoles() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All Roles fetched successfully!",
                        roleService.getAllRoles(),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Create Role",
            description = "Creates a new user role based on the provided data."
    )
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

    @Operation(
            summary = "Delete Role",
            description = "Deletes an existing user role by its ID."
    )
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
