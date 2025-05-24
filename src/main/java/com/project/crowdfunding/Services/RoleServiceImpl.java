package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Role;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.UserRoles;
import com.project.crowdfunding.Repository.RoleRepository;
import com.project.crowdfunding.dto.request.RoleRequestDto;
import com.project.crowdfunding.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;


    @Override
    public Role createRole(RoleRequestDto roleDto) {
        UserRoles roles = UserRoles.fromString(roleDto.getRoleName());

        Optional<Role> optRole = roleRepository.findByRoleName(roles);
        if(optRole.isPresent()){
            throw new IllegalArgumentException("Role of name: "+ roles+" already exist!");
        }
        Role role = modelMapper.map(roleDto, Role.class);
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Role not exist with id: "+ id));
        roleRepository.delete(role);
    }

    @Override
    public UserResponseDto assignRole(String username, String role) {
       User user = userService.getByUsername(username);

       UserRoles roles = UserRoles.fromString(role);
       Role assignRole = roleRepository.findByRoleName(roles).orElseThrow(()-> new IllegalArgumentException("Role doesn't exist!"));
       user.getRoles().add(assignRole);
       User savedUser = userService.saveUser(user);
       return modelMapper.map(savedUser, UserResponseDto.class);
    }
}
