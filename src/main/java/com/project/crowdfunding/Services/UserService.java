package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.KycStatus;
import com.project.crowdfunding.dto.request.PasswordResetDto;
import com.project.crowdfunding.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserById(Long id);
    User getByUsername(String username);
    String getKycStatusByUsername(String username);
    List<UserResponseDto> getUserByKycStatus(String status);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(Long id);
    void resetPassword(PasswordResetDto passwordResetDto);
    boolean userExistByEmail(String email);
    KycStatus findKycStatusByUserId(Long userId);
}
