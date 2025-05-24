package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Tag;
import com.project.crowdfunding.dto.request.TagRequestDto;

import java.util.List;

public interface TagService {
    Tag createTag(TagRequestDto tag);
    List<Tag> getAllTags();
    void delete(Long id);
}

