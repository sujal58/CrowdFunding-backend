package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Comment;
import com.project.crowdfunding.dto.request.CommentRequestDto;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();
    Comment createComment(Long campaignId, CommentRequestDto comment);
    List<Comment> getCommentsByCampaignId(Long campaignId);
    void deleteComment(Long id);
}
