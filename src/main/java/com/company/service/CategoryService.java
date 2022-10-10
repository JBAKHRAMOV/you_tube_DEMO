package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.exception.AppBadRequestException;
import com.company.exception.CategoryAlreadyExistsException;
import com.company.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /** CREATE category */
    public CategoryDTO create(CategoryDTO dto) {

        var optional = categoryRepository.findByName(dto.getName());

        if (optional.isPresent()) {
            log.warn("category already exists : {}", dto );
            throw new CategoryAlreadyExistsException("Category Already Exists");
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

        return new PageImpl<>(dtoList, pageable, totalElement);
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
        var optional1 = categoryRepository.findByName(dto.getName());
        if (optional.isEmpty()) {
            log.warn("id not found : {}", id );
            throw new AppBadRequestException("Id Not Found");
        }

        if (optional1.isPresent()) {
            log.warn("category already exists : {}", dto );
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        CategoryEntity category = optional.get();
        category.setName(dto.getName());
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
