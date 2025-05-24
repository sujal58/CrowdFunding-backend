package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Tag;
import com.project.crowdfunding.Repository.TagRepository;
import com.project.crowdfunding.dto.request.TagRequestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final ModelMapper modelMapper;

    @Override
    public Tag createTag(TagRequestDto request) {
        Tag tag = modelMapper.map(request, Tag.class);
        tag.setCreatedAt(LocalDateTime.now());
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}

