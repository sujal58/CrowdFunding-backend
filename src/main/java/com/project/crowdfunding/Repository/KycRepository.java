package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KycRepository extends JpaRepository<Kyc, Long> {
    Optional<Kyc> findByUserId(Long userId);
}
