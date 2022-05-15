package com.company.service;

import com.company.changeDto.ChannelDto;
import com.company.entity.ChannelEntity;
import com.company.enums.GeneralStatus;
import com.company.exception.ChannelAlredyExistsException;
import com.company.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    public ChannelDto create(ChannelDto  dto){
        if (channelRepository.findByName(dto.getName()).get() !=null){
            throw  new ChannelAlredyExistsException("Channel alredy exists");
        }
        ChannelEntity entity=new ChannelEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setKey(UUID.randomUUID().toString());
        entity.setStatus(GeneralStatus.ACTIVE);
        channelRepository.save(entity);
        dto.setCreateDate(entity.getCreateDate());
        dto.setKey(entity.getKey());
        dto.setId(entity.getId());

        return dto;
    }


}
