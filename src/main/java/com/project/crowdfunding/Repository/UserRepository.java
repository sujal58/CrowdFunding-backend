package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.KycStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findByKycStatus(KycStatus status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    long count();

    @Query("SELECT u.kycStatus FROM User u WHERE u.userId = :userId")
    KycStatus findKycStatusByUserId(@Param("userId") Long userId);
}
