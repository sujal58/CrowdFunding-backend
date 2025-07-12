package com.project.crowdfunding.Config;


import com.project.crowdfunding.Entity.Role;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.UserRoles;
import com.project.crowdfunding.Repository.RoleRepository;
import com.project.crowdfunding.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;


@Configuration
public class AppConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }



    @Component
    @RequiredArgsConstructor
    public class dataSeeder implements CommandLineRunner {

        private final UserRepository userRepository;

        private final RoleRepository roleRepository;

        @Override
        public void run(String... args) throws Exception {

            Role adminRole = roleRepository.findByRoleName(UserRoles.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(UserRoles.ROLE_ADMIN)));

            Role creatorRole = roleRepository.findByRoleName(UserRoles.ROLE_CREATOR)
                    .orElseGet(() -> roleRepository.save(new Role(UserRoles.ROLE_CREATOR)));


            if(userRepository.findByUsername("admin").isEmpty()){
                User user = new User();
                user.setEmail("sujal.admin@gmail.com");
                user.setUsername("admin");
                user.setPassword(passwordEncoder().encode("admin@123"));
                user.setName("Admin admin");
                user.setCity("Butwal");
                user.setCountry("Nepal");
                user.setRoles(Set.of(adminRole));
                userRepository.save(user);
            }

            if(userRepository.findByUsername("sujal").isEmpty()){
                User user = new User();
                user.setEmail("sujal.creator@gmail.com");
                user.setUsername("sujal");
                user.setPassword(passwordEncoder().encode("sujal@123"));
                user.setName("Sujal Pandey");
                user.setCity("Butwal");
                user.setCountry("Nepal");
                user.setRoles(Set.of(creatorRole));
                userRepository.save(user);
            }

            if(roleRepository.findByRoleName(UserRoles.ROLE_CREATOR).isEmpty()){
                roleRepository.save(new Role(UserRoles.ROLE_CREATOR));
            }

            if(roleRepository.findByRoleName(UserRoles.ROLE_USER).isEmpty()){
                roleRepository.save(new Role(UserRoles.ROLE_USER));
            }

        }
    }


}
