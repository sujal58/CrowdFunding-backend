package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Tag;

import java.util.List;

public interface TagService {
    Tag createTag(Tag tag);
    List<Tag> getAllTags();
}

