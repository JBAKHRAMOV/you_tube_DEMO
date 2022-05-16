package com.company.service;

import com.company.dto.TagDTO;
import com.company.entity.TagEntity;
import com.company.exception.ItemAlreadyExistsException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    /** CREATE */
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





    /** GET PAGINATION LIST */
    public PageImpl<TagDTO> getList(int page, int size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TagEntity> pagination = tagRepository.findAll(pageable);

        List<TagEntity> entityList = pagination.getContent();

        long totalElement = pagination.getTotalElements();

        List<TagDTO> dtoList = entityList.stream().map(this::toDTO).toList();

        return new PageImpl<TagDTO>(dtoList, pageable, totalElement);
    }

    /** UPDATE */
    public TagDTO update(Integer id, TagDTO dto) {

        TagEntity entity = tagRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Not Found!"));


        entity.setName(dto.getName());

        tagRepository.save(entity);

        return toDTO(entity);
    }

    /** DELETE */
    public Boolean delete(Integer id) {

        tagRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Not Found!"));

        tagRepository.deleteById( id);

        return true;
    }


    /**
     *    ASSISTANT METHODS
     */

    private TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
