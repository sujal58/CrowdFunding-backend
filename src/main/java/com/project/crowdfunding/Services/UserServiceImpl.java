package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.KycStatus;
import com.project.crowdfunding.Exception.ResourceNotFoundException;
import com.project.crowdfunding.Repository.UserRepository;
import com.project.crowdfunding.dto.request.PasswordResetDto;
import com.project.crowdfunding.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

//    private final AuthHelper authHelper;


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+ id));
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found having username: "+ username));
    }

    @Override
    public String getKycStatusByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found with username: "+ username));
        return user.getKycStatus().toString();

    }

    @Override
    public List<UserResponseDto> getUserByKycStatus(String status) {
        if(status.isEmpty()){
            throw new IllegalArgumentException("Campaign status is required!");
        }

        List<User> users = userRepository.findByKycStatus(KycStatus.fromString(status));
        return users.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).toList();
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found with email: "+ email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+ id));
        userRepository.delete(user);
    }

    @Override
    public void resetPassword(PasswordResetDto passwordResetDto) {
        User user = getUserByEmail(passwordResetDto.getEmail());
        String messageType = passwordResetDto.getMessage();

        if ("forgot".equalsIgnoreCase(messageType)) {
            updatePassword(user, passwordResetDto.getNewPassword());
        } else {
            validateOldPassword(passwordResetDto.getOldPassword(), user.getPassword());
            updatePassword(user, passwordResetDto.getNewPassword());
        }
    }

    @Override
    public boolean userExistByEmail(String email) {
        return userRepository.existsByEmail(email);

    }

    @Override
    public KycStatus findKycStatusByUserId(Long userId) {
        return userRepository.findKycStatusByUserId(userId);
    }


    private void validateOldPassword(String oldPassword, String currentHashedPassword) {
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Old password is required");
        }

        boolean isMatched = passwordEncoder.matches(oldPassword, currentHashedPassword);
        if (!isMatched) {
            throw new IllegalArgumentException("Password not matched!");
        }
    }

    private void updatePassword(User user, String newPassword) {
        if(passwordEncoder.matches(newPassword, user.getPassword())){
            throw new IllegalArgumentException("New password cannot be similar to current");
        }
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}

