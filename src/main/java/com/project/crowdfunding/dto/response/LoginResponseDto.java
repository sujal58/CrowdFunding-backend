package com.project.crowdfunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String username;

    private String token;

    private List<String> roles;

}
