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

<<<<<<< HEAD
    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity category = categoryRepository.findByName(dto.getName());
        if (category != null) {
            log.warn("category alredy exists : {}", dto );
            throw new CategoryAlredyExistsException("Category Already Exists");
        }
=======
    /** CREATE category */
    public CategoryDTO create(CategoryDTO dto) {

        CategoryEntity category = categoryRepository.findByName(dto.getName());

        if (category != null) {

            log.warn("category alredy exists : {}", dto );

            throw new CategoryAlredyExistsException("Category Already Exists");
        }

>>>>>>> 243834d (Initial commit)
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());

        categoryRepository.save(entity);
<<<<<<< HEAD
        dto.setId(entity.getId());
        return dto;
    }

    public PageImpl<CategoryDTO> getList(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CategoryEntity> pagination = categoryRepository.findAll(pageable);

        List<CategoryEntity> profileEntityList = pagination.getContent();
        long totalElement = pagination.getTotalElements();
        List<CategoryDTO> dtoList = profileEntityList.stream().map(this::toDTO).toList();
        return new PageImpl<CategoryDTO>(dtoList, pageable, totalElement);
    }

    public CategoryDTO getById(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("id not found : {}", id );
            throw new AppBadRequestException("Id Not Found");
        }
        CategoryEntity category = optional.get();
        return toDTO(category);
    }

    public CategoryDTO getById(Integer id, LangEnum lang) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("id not found : {}", id );
            throw new AppBadRequestException("Id Not Found");
        }
        CategoryEntity category = optional.get();
        return toDTO(category, lang);
    }

    public String update(Integer id, CategoryDTO dto,Integer pId) {
        System.out.println(pId);
        ProfileEntity profile = profileService.get(pId);
        if (!profile.getRole().equals(ProfileRole.USER)) {
            log.warn("not access : {}", dto );
            throw new AppForbiddenException("Not access");
        }
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
=======

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

>>>>>>> 243834d (Initial commit)
        if (optional.isEmpty()) {
            log.warn("id not found : {}", id );
            throw new AppBadRequestException("Id Not Found");
        }
<<<<<<< HEAD
        CategoryEntity entity = categoryRepository.findByName(dto.getName());
=======

>>>>>>> 243834d (Initial commit)
        if (entity != null) {
            log.warn("category alredy exists : {}", dto );
            throw new CategoryAlredyExistsException("Category alredy exists");
        }
<<<<<<< HEAD
        CategoryEntity category = optional.get();
        entity.setName(dto.getName());
        categoryRepository.save(category);
        return "Success";
    }

    public String delete(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("id not found : {}", id );
            throw new AppBadRequestException("Id Not Found");
        }
        CategoryEntity entity = optional.get();
        categoryRepository.delete(entity);
        return "Success";
    }

    private CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    private CategoryDTO toDTO(CategoryEntity entity, LangEnum lang) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        switch (lang) {
            case uz:
                dto.setName(entity.getName());
                break;
            case ru:
                dto.setName(entity.getName());
                break;
            case en:
                dto.setName(entity.getName());
                break;
        }

        return dto;
    }

    public List<CategoryDTO> getRegionList(LangEnum lang) {
        List<CategoryEntity> entityList = categoryRepository.findAll();

        List<CategoryDTO> list = new ArrayList<>();
        for (CategoryEntity entity : entityList) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            switch (lang) {
                case uz -> dto.setName(entity.getName());

                case en -> dto.setName(entity.getName());

                case ru -> dto.setName(entity.getName());
            }
            list.add(dto);
        }
        return list;
    }
=======

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
>>>>>>> 243834d (Initial commit)
}
