package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Role;
import com.project.crowdfunding.Enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(UserRoles role);
}
