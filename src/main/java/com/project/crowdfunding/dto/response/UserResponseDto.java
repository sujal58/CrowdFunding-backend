package com.project.crowdfunding.dto.response;

import com.project.crowdfunding.Entity.Role;
import com.project.crowdfunding.Enums.KycStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long userId;
    private String email;
    private String name;
    private String username;
    private String country;
    private String city;
    private KycStatus kycStatus;
    private Set<Role> roles;
    private LocalDateTime createdAt;
}
