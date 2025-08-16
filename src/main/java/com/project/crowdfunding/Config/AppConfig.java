package com.project.crowdfunding.Config;


import com.project.crowdfunding.Entity.*;
import com.project.crowdfunding.Enums.UserRoles;
import com.project.crowdfunding.Repository.RoleRepository;
import com.project.crowdfunding.Repository.UserRepository;
import com.project.crowdfunding.dto.response.CampaignResponseDto;
import com.project.crowdfunding.dto.response.DonationResponseDto;
import com.project.crowdfunding.dto.response.KycResponseDto;
import com.project.crowdfunding.dto.response.NotificationResponseDto;
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
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Campaign.class, CampaignResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getUsername(), CampaignResponseDto::setUsername);
        });
        modelMapper.typeMap(Donation.class, DonationResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getCampaign().getTitle(), DonationResponseDto::setCampaignName);
            mapper.map(src-> src.getPayment().getPaymentId(), DonationResponseDto::setTransactionId);
        });

        modelMapper.typeMap(Kyc.class, KycResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getKycStatus(), KycResponseDto::setStatus);
        });

        modelMapper.typeMap(Notification.class, NotificationResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getUsername(), NotificationResponseDto::setUsername);
        });

        return modelMapper;
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

            Role userRole = roleRepository.findByRoleName(UserRoles.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(UserRoles.ROLE_USER)));


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
                user.setEmail("pandeysujal258@gmail.com");
                user.setUsername("sujal");
                user.setPassword(passwordEncoder().encode("sujal@123"));
                user.setName("Sujal Pandey");
                user.setCity("Butwal");
                user.setCountry("Nepal");
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
            }

            if(roleRepository.findByRoleName(UserRoles.ROLE_ADMIN).isEmpty()){
                roleRepository.save(new Role(UserRoles.ROLE_ADMIN));
            }

            if(roleRepository.findByRoleName(UserRoles.ROLE_USER).isEmpty()){
                roleRepository.save(new Role(UserRoles.ROLE_USER));
            }

        }
    }


}
