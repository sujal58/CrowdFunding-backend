package com.project.crowdfunding.Config;

import com.project.crowdfunding.Exception.CustomAccessDeniedHandler;
import com.project.crowdfunding.Services.AuthService.UserDetailsServiceImpl;
import com.project.crowdfunding.utils.AuthEntryPointJwt;
import com.project.crowdfunding.utils.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain customSecurityFilter(HttpSecurity http) throws Exception{

        return http
                .authorizeHttpRequests(request ->
                        request.requestMatchers(Role_Creator).hasAuthority("ROLE_CREATOR")
                                .requestMatchers("/api/v1/roles","/api/v1/users", "/api/v1/kyc/status/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("api/v1/kyc/submit").authenticated()
                                .anyRequest().permitAll()
                        )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex ->
                        ex
                                .authenticationEntryPoint(authEntryPointJwt)
                                .accessDeniedHandler(accessDeniedHandler)

                )
                .build();
    }


    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    private String[] Role_Creator =
            {
                "/api/v1/campaigns",
                "/api/v1/campaigns/delete/**",
                "api/v1/campaigns/total"
            };
}
