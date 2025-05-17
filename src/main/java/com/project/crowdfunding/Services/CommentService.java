package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    List<Comment> getCommentsByCampaignId(Long campaignId);
    void deleteComment(Long id);
}
