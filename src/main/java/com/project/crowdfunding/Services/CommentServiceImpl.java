package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Campaign;
import com.project.crowdfunding.Entity.Comment;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Repository.CampaignRepository;
import com.project.crowdfunding.Repository.CommentRepository;
import com.project.crowdfunding.dto.request.CommentRequestDto;
import com.project.crowdfunding.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private ModelMapper modelMapper;

    private final AuthHelper authHelper;

    private final CampaignRepository campaignRepository;


    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment createComment(Long campaignId, CommentRequestDto dto) {
        User user = authHelper.getAuthenticatedUser();
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(()-> new IllegalArgumentException("Campaign not exist with id: "+ campaignId));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setCampaign(campaign);
        comment.setContent(dto.getContent());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByCampaignId(Long campaignId) {
        List<Comment> comments = commentRepository.findByCampaignCampaignId(campaignId);
        if(!comments.isEmpty()){
            System.out.println("comments exist");
        }
        return comments;
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}

