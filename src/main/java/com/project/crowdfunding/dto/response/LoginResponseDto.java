package com.project.crowdfunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String userId;

    private String token;

    private List<String> roles;

    private String status;

}
