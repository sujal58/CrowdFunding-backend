package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserById(Long id);
    User getByUsername(String username);
    String getKycStatusByUsername(String username);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(Long id);
}
