package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCampaignId(Long campaignId);

    List<Comment> findByUserId(Long userId);
}
