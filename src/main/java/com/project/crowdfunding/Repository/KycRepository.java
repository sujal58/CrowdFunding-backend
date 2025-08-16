package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Kyc;
import com.project.crowdfunding.Enums.KycStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface KycRepository extends JpaRepository<Kyc, Long> {
    Optional<Kyc> findByUserUserId(Long userId);

    boolean existsByDocumentNumber(String docNumber);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(k) FROM Kyc k WHERE k.user.kycStatus = :status")
    Long countTotalPendingKyc( @Param("status") KycStatus status);

}
