package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Donation;
import com.project.crowdfunding.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Optional<Donation> findByDonationId(Long donationId);

    List<Donation> findByUser(User user);

    List<Donation> findByCampaignCampaignId(Long campaignId);
}
