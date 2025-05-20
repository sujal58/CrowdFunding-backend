package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByStatus(String status);

    List<Campaign> findByUserUserId(Long userId);
}
