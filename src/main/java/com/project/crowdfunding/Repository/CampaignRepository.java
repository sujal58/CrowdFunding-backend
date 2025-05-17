package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByStatus(String status);

    List<Campaign> findByUserId(Long userId);
}
