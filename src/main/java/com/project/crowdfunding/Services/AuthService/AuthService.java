package com.project.crowdfunding.Services.AuthService;

import com.project.crowdfunding.Entity.Role;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.UserRoles;
import com.project.crowdfunding.Repository.RoleRepository;
import com.project.crowdfunding.Repository.UserRepository;
import com.project.crowdfunding.dto.request.LoginRequestDto;
import com.project.crowdfunding.dto.request.SignUpRequestDto;
import com.project.crowdfunding.dto.response.LoginResponseDto;
import com.project.crowdfunding.dto.response.SignUpResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final JwtService jwtService;



    @Transactional
    public SignUpResponseDto registerUser(SignUpRequestDto dto){
        if(userRepository.existsByUsername(dto.getUsername())){
            throw new RuntimeException("User having same username already exist");
        }

        if(userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("User having same Email already exist");
        }

        // Hash the password before constructing the User
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);

        User user = modelMapper.map(dto, User.class);

        user.setRoles(assignRoles(dto.getRoles()));
        User savedUser = userRepository.save(user);
        return new SignUpResponseDto(savedUser.getUserId(),
                savedUser.getEmail(),
                savedUser.getUsername(),
                savedUser.getRoles()
        );
    }

    public LoginResponseDto login(LoginRequestDto dto){

        Optional<User> optionalUser = userRepository.findByUsername(dto.getEmail_username());

        if(optionalUser.isEmpty()){
            optionalUser = userRepository.findByEmail(dto.getEmail_username());
        }

        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Invalid username or Email!");
        }

        User user = optionalUser.get();

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), dto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String JwtToken = jwtService.GenerateToken(userDetails.getUsername());

            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new LoginResponseDto(userDetails.getUsername(), JwtToken, roles);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid username and password!");
        }catch (Exception e){
            throw new RuntimeException("Exception while login: "+ e.getMessage());
        }

    }


    private Set<Role> assignRoles(Set<String> roles) {
        // If no roles are provided, assign the default role: ROLE_USER
        if (roles == null || roles.isEmpty()) {
            return Set.of(getRole(UserRoles.ROLE_DONOR));
        }

        // Convert input strings to corresponding UserRole enums
        Set<UserRoles> mappedRoles = new HashSet<>();

        for (String roleStr : roles) {
            // Assign ROLE_ADMIN if the string is "admin" (case-insensitive)
            // Otherwise, default to ROLE_USER
            if ("admin".equalsIgnoreCase(roleStr)) {
                mappedRoles.add(UserRoles.ROLE_ADMIN);
            } else if("creator".equalsIgnoreCase(roleStr)) {
                mappedRoles.add(UserRoles.ROLE_CREATOR);
            }else {
                mappedRoles.add(UserRoles.ROLE_DONOR);
            }
        }

        // Convert each UserRole enum to its corresponding Role entity
        return mappedRoles.stream()
                .map(this::getRole)
                .collect(Collectors.toSet());
    }

    @Cacheable("roles")
    private Role getRole(UserRoles userRole) {
        return roleRepository.findByRoleName(userRole)
                .orElseThrow(() -> new RuntimeException("Role " + userRole + " not found!"));
    }
}
