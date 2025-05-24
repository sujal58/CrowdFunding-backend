package com.project.crowdfunding.utils;

import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthHelper {


    private final UserService userService;

    public String getAuthenticatedUsername(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()){
            UserDetails user = (UserDetails) auth.getPrincipal();
            return user.getUsername();
        }else{
            throw new BadCredentialsException("Unauthorized access found!");
        }
    }

    public User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()){
            UserDetails user = (UserDetails) auth.getPrincipal();
           return userService.getByUsername(user.getUsername());

        }else{
            throw new BadCredentialsException("Unauthorized access found!");
        }
    }
}
