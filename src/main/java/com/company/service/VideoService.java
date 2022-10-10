package com.company.service;

import com.company.changeDto.VideoDetailDTO;
import com.company.dto.CategoryDTO;
import com.company.dto.VideoDto;
import com.company.entity.VideoEntity;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ChannelRepository;
import com.company.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final AttachService attachService;
    private final ChannelRepository channelRepository;
    private final ChannelService channelService;
    private VideoLIkeService videoLIkeService;


    /**
     * CREATE video
     */
    public VideoDto create(Integer pId, VideoDto dto) {
        channelRepository.findByProfileIdAndId(pId, dto.getChannelId())
                .orElseThrow(() -> {
                    throw new ItemNotFoundException("Item not found  or you are not the channel owner");
                });

        VideoEntity entity = new VideoEntity();
        entity.setKey(UUID.randomUUID());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setType(dto.getType());
        entity.setViewCount(0);
        entity.setSharedCount(0);
        if (dto.getPreviewAttachId() != null) {
            entity.setPreviewAttachId(dto.getPreviewAttachId());
        }
        entity.setCategoryId(dto.getCategoryId());
        entity.setVideoId(dto.getVideoId());
        entity.setChannelId(dto.getChannelId());
        entity.setCreateDate(LocalDateTime.now());

        videoRepository.save(entity);

        return toDTO(entity);
    }

    public VideoDto updateDetail(Integer pId, VideoDetailDTO dto) {
        VideoEntity entity = videoRepository.findByIdAndKey(pId, UUID.fromString(dto.getKey()))
                .orElseThrow(() -> {
                    throw new ItemNotFoundException("Item not found");
                });
        if (!dto.getChannelId().equals(entity.getChannelId())) {
            throw new AppBadRequestException("you are not the channel owner");
        }
        entity.setTitle(dto.getTitle());
        entity.setDescription(entity.getDescription());
        entity.setCategoryId(dto.getCategoryId());
        entity.setPreviewAttachId(dto.getPreviewAttachId());

        return toDTO(entity);
    }

    public VideoDto toDTO(VideoEntity entity) {
        VideoDto dto = new VideoDto();
        dto.setKey(entity.getKey());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());
        dto.setViewCount(entity.getViewCount());
        dto.setSharedCount(entity.getSharedCount());
        if (entity.getPreviewAttach() != null) {
            dto.setPreviewAttachURL(attachService.getPhotoURL(entity.getPreviewAttach()));
        }
        dto.setCategoryDTO(new CategoryDTO(entity.getCategory().getId(), entity.getCategory().getName()));
        dto.setVideoAttachURL(attachService.getPhotoURL(entity.getVideoAttach()));
        dto.setChannelDto(channelService.toDTO(entity.getChannel()));
        dto.setCreateDate(entity.getCreateDate());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setLikeCountDTO(videoLIkeService.getVideoLikeCount(entity.getKey().toString()));
        return dto;
    }
}
