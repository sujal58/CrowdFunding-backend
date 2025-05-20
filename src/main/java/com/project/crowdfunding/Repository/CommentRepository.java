package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCampaignCampaignId(Long campaignId);

    List<Comment> findByUserUserId(Long userId);
}
