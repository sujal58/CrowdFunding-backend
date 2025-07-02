package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.UserService;
import com.project.crowdfunding.dto.response.ApiResponse;
import com.project.crowdfunding.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for user management and retrieval")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final HttpServletRequest servletRequest;

    @Operation(
            summary = "Get User by ID",
            description = "Fetches user details by user ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        UserResponseDto userDto = modelMapper.map(userService.getUserById(id), UserResponseDto.class);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "User fetched successfully!",
                        userDto,
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get All Users",
            description = "Retrieves a list of all users."
    )
    @GetMapping
    public ResponseEntity<ApiResponse> getAllUser() {
        List<UserResponseDto> users = userService.getAllUsers().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "All users fetched successfully!",
                        users,
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get KYC Status by Username",
            description = "Returns the KYC verification status for a specific username."
    )
    @GetMapping("/kyc/{user}")
    public ResponseEntity<ApiResponse> getKycStatusByUsername(@RequestParam String user) {
        String response = userService.getKycStatusByUsername(user);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "KYC status fetched successfully!",
                        Map.of("username", user, "status", response),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get All User Details",
            description = "Fetches detailed information of all users."
    )
    @GetMapping("/details")
    public ResponseEntity<ApiResponse> getAllUserDetails() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All users fetched successfully!",
                        userService.getAllUsers(),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Delete User",
            description = "Deletes a user by their ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "User deleted successfully!"
                )
        );
    }

//    @Operation(
//        summary = "Update User",
//        description = "Updates user information by user ID."
//    )
//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse> update(
//            @PathVariable Long id,
//            @RequestBody UserRequestDto request
//    ) {
//        UserResponseDto updatedUser = userService.update(id, request);
//        return ResponseEntity.ok(
//                ApiResponse.success(
//                        "User updated successfully!",
//                        updatedUser,
//                        servletRequest.getRequestURI()
//                )
//        );
//    }
}
