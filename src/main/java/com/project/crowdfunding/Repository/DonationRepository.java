package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorId(Long donorId);

    List<Donation> findByCampaignId(Long campaignId);
}
