package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonationId(Long donationId);

    List<Donation> findByCampaignCampaignId(Long campaignId);
}
