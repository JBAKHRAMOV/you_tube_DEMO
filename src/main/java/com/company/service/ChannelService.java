package com.company.service;

import com.company.changeDto.ChannelStatusDTO;
import com.company.dto.ChannelDto;
import com.company.entity.ChannelEntity;
import com.company.enums.GeneralStatus;
import com.company.enums.ProfileRole;
import com.company.exception.ChannelAlreadyExistsException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ChannelRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final AttachService attachService;


    public ChannelDto create(Integer pId, ChannelDto dto){
        var optional = channelRepository.findByName(dto.getName());
        if (optional.isPresent()){
            throw  new ChannelAlreadyExistsException("Channel already exists");
        }

        ChannelEntity entity= new ChannelEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setKey(UUID.randomUUID().toString());
        entity.setStatus(GeneralStatus.ACTIVE);

        channelRepository.save(entity);

        dto.setCreateDate(entity.getCreateDate());
        dto.setKey(entity.getKey());
        dto.setId(entity.getId());

        entity.setProfileId(pId);

        channelRepository.save(entity);

        return toDTO(entity);
    }

    public ChannelDto update(Integer pId, String key,ChannelDto  dto){
        ChannelEntity entity = channelRepository.findByProfileIdAndKey(pId,key)
                .orElseThrow(()->{throw  new ItemNotFoundException("Item not found");});

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setUpdateDate(LocalDateTime.now());

        channelRepository.save(entity);

        return toDTO(entity);
    }

    public ChannelDto updatePhoto(Integer pId, String key, MultipartFile file){
        ChannelEntity entity = channelRepository.findByProfileIdAndKey(pId,key)
                .orElseThrow(()->{throw  new ItemNotFoundException("Item not found");});

        if (entity.getPhoto()==null){
            entity.setPhoto(attachService.uploadGeneric(file));
        }
        else if (entity.getPhoto()!=null){
            entity.setPhoto(attachService.updateGeneric(file,entity.getPhoto().getId()));
        }
        channelRepository.save(entity);

        return toDTO(entity);
    }

    public ChannelDto updateBanner(Integer pId, String key, MultipartFile file){
        ChannelEntity entity = channelRepository.findByProfileIdAndKey(pId,key)
                .orElseThrow(()->{throw  new ItemNotFoundException("Item not found");});

        if (entity.getBanner()==null){
            entity.setBanner(attachService.uploadGeneric(file));
        }

        else if (entity.getBanner()!=null){
            entity.setBanner(attachService.updateGeneric(file,entity.getBanner().getId()));
        }
        channelRepository.save(entity);

        return toDTO(entity);
    }

    public PageImpl<ChannelDto> getPaginationList(int page, int size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ChannelEntity> pagination = channelRepository.findAll(pageable);

        List<ChannelEntity> entityList = pagination.getContent();

        long totalElement = pagination.getTotalElements();

        List<ChannelDto> dtoList = entityList.stream().map(this::toDTO).toList();

        return new PageImpl<>(dtoList, pageable, totalElement);
    }

    public GeneralStatus changeStatus(Integer pId, ProfileRole role, ChannelStatusDTO dto){
        if (role.equals(ProfileRole.ADMIN)){
            ChannelEntity entity=channelRepository.findByKey(dto.getKey())
                    .orElseThrow(()-> {throw  new ItemNotFoundException("Item not found");});
            entity.setStatus(dto.getStatus());
            channelRepository.save(entity);
            return entity.getStatus();
        }
        ChannelEntity entity=channelRepository.findByProfileIdAndKey(pId,dto.getKey())
                    .orElseThrow(()-> {throw  new ItemNotFoundException("Item not found");});
        entity.setStatus(dto.getStatus());
        channelRepository.save(entity);
        return entity.getStatus();
    }

    public PageImpl<ChannelDto> getUserPaginationList(Integer pId, int page, int size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ChannelEntity> pagination = channelRepository.findByProfileId(pId,pageable);

        List<ChannelEntity> entityList = pagination.getContent();

        long totalElement = pagination.getTotalElements();

        List<ChannelDto> dtoList = entityList.stream().map(this::toDTO).toList();

        return new PageImpl<>(dtoList, pageable, totalElement);
    }





    public ChannelDto toDTO(ChannelEntity entity){
        ChannelDto dto=new ChannelDto();
        dto.setKey(entity.getKey());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCreateDate(entity.getCreateDate());
        if (entity.getPhoto()!=null){
            dto.setPhotoURL(attachService.getPhotoURL(entity.getPhoto()));
        }else if (entity.getBanner()!=null){
            dto.setBannerURL(attachService.getPhotoURL(entity.getBanner()));
        }
        return dto;
    }


}
