package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Role;
import com.project.crowdfunding.Enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Long, Role> {

    Optional<Role> findByRoleName(UserRoles role);
}
