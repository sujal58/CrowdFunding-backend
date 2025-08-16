package com.project.crowdfunding.Services.AuthService;

import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       if(username.isEmpty()){
           throw new IllegalArgumentException("Username not found!");
       }

        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User having username "+ username +" found!"));

       return new CustomUserDetails(user);
    }

    public UserDetails loadUserByUsernameOrEmail(String input) {
        User user;
        if (input.contains("@")) {
            user = userRepository.findByEmail(input)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + input));
        } else {
            user = userRepository.findByUsername(input)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + input));
        }
        return new CustomUserDetails(user);
    }

}
