package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface KycRepository extends JpaRepository<Kyc, Long> {
    Optional<Kyc> findByUserUserId(Long userId);
}
