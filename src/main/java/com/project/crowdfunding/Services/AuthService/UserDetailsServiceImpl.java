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
}
