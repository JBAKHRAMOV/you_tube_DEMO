package com.company.service;

import com.company.dto.TagDTO;
import com.company.entity.TagEntity;
import com.company.enums.LangEnum;
import com.company.exception.ItemAlreadyExistsException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ProfileService profileService;

    public TagDTO create(TagDTO dto) {


        Optional<TagEntity> optional = tagRepository.findByName(dto.getName());
        if (optional.isPresent()) {
            log.warn("tag alredy used : {}", dto );
            throw new ItemAlreadyExistsException("This Tag already used!");
        }

        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        tagRepository.save(entity);
        return toDTO(entity);
    }



    public TagDTO update(Integer id, TagDTO dto) {
//        ProfileEntity profileEntity = profileService.get(pId);


        TagEntity entity = tagRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Not Found!"));


        entity.setName(dto.getName());
        tagRepository.save(entity);
        return toDTO(entity);
    }

    public Boolean delete(Integer id) {
        TagEntity entity = tagRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Not Found!"));

        tagRepository.deleteById( id);
        return true;
    }

    private TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
