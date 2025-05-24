package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.UserService;
import com.project.crowdfunding.dto.response.ApiResponse;
import com.project.crowdfunding.dto.response.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final HttpServletRequest servletRequest;


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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "User deleted successfully!"
                )
        );
    }


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
