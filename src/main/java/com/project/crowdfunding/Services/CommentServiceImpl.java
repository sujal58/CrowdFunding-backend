package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Comment;
import com.project.crowdfunding.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByCampaignId(Long campaignId) {
        return commentRepository.findByCampaignCampaignId(campaignId);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}

