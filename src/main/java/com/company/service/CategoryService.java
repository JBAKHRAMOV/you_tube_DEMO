package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.exception.AppBadRequestException;
import com.company.exception.AppForbiddenException;
import com.company.exception.CategoryAlredyExistsException;
import com.company.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CategoryRepository categoryRepository;

    /** CREATE category */
    public CategoryDTO create(CategoryDTO dto) {

        CategoryEntity category = categoryRepository.findByName(dto.getName());

        if (category != null) {

            log.warn("category alredy exists : {}", dto );

            throw new CategoryAlredyExistsException("Category Already Exists");
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());

        categoryRepository.save(entity);

        dto.setId(entity.getId());

        return dto;
    }

    /** GET PAGINATION LIST */
    public PageImpl<CategoryDTO> getList(int page, int size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CategoryEntity> pagination = categoryRepository.findAll(pageable);

        List<CategoryEntity> entityList = pagination.getContent();

        long totalElement = pagination.getTotalElements();

        List<CategoryDTO> dtoList = entityList.stream().map(this::toDTO).toList();

        return new PageImpl<CategoryDTO>(dtoList, pageable, totalElement);
    }

    /** GET BY ID */
    public CategoryDTO getById(Integer id) {

        Optional<CategoryEntity> optional = categoryRepository.findById(id);

        if (optional.isEmpty()) {

            log.warn("id not found : {}", id );

            throw new AppBadRequestException("Id Not Found");
        }

        CategoryEntity category = optional.get();

        return toDTO(category);
    }

    /** UPDATE */
    public String update(Integer id, CategoryDTO dto) {

        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        CategoryEntity entity = categoryRepository.findByName(dto.getName());
        if (optional.isEmpty()) {
            log.warn("id not found : {}", id );
            throw new AppBadRequestException("Id Not Found");
        }

        if (entity != null) {
            log.warn("category alredy exists : {}", dto );
            throw new CategoryAlredyExistsException("Category alredy exists");
        }

        CategoryEntity category = optional.get();
        entity.setName(dto.getName());
        categoryRepository.save(category);

        return "Success";
    }

    /** DELETE */
    public String delete(Integer id) {

        Optional<CategoryEntity> optional = categoryRepository.findById(id);

        if (optional.isEmpty()) {

            log.warn("id not found : {}", id );

            throw new AppBadRequestException("Id Not Found");
        }

        categoryRepository.deleteById(id);

        return "Success";
    }




    /**
     * ASSISTANT METHODS
     */
    private CategoryDTO toDTO(CategoryEntity entity) {

        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }
}
