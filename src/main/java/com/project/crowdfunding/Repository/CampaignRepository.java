package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Campaign;
import com.project.crowdfunding.Enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findByStatus(CampaignStatus status);

    List<Campaign> findByUserUserId(Long userId);

    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.user.userId = :userId")
    Long countCampaignsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.user.userId = :userId AND c.status = :status")
    Long countActiveCampaignsByUser(@Param("userId") Long userId, @Param("status") CampaignStatus status);

    long count();
}
