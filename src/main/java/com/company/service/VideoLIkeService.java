package com.company.service;

import com.company.dto.LikeCountDTO;
import com.company.dto.VideoLikeDto;
import com.company.entity.VideoLikeEntity;
import com.company.enums.LikeType;
import com.company.exception.ItemNotFoundException;
import com.company.exception.VideoLikeAlreadyExistsException;
import com.company.repository.VideoLikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VideoLIkeService {

    private final VideoLikeRepository videoLikeRepository;

    public VideoLikeDto create(Integer pId,VideoLikeDto dto){
        videoLikeRepository.findByProfileIdAndVideoKey(pId, dto.getVideoKey())
                .orElseThrow(()->{throw new VideoLikeAlreadyExistsException("Already exists");});

        VideoLikeEntity entity=new VideoLikeEntity();
        entity.setProfileId(pId);
        entity.setVideoKey(dto.getVideoKey());
        entity.setType(dto.getType());
        entity.setCreatedDate(LocalDateTime.now());

        return toDTO(entity);
    }

    public VideoLikeDto read(Integer pId,String key){
        VideoLikeEntity entity=videoLikeRepository.findByProfileIdAndVideoKey(pId, key)
                .orElseThrow(()->{throw new ItemNotFoundException("Item not found");});

        return toDTO(entity);
    }

    public VideoLikeDto update(Integer pId,VideoLikeDto dto){
        VideoLikeEntity entity=videoLikeRepository.findByProfileIdAndVideoKey(pId, dto.getVideoKey())
                .orElseThrow(()->{throw new ItemNotFoundException("Item not found");});

        if (dto.getType().equals(LikeType.DISLIKE)){
            videoLikeRepository.updateType(LikeType.LIKE,pId);
            entity.setType(LikeType.LIKE);
        }else if (dto.getType().equals(LikeType.LIKE)){
            videoLikeRepository.updateType(LikeType.DISLIKE,pId);
            entity.setType(LikeType.DISLIKE);
        }

        return toDTO(entity);
    }

    public LikeCountDTO getVideoLikeCount(String key){
        List<VideoLikeEntity>entityList=videoLikeRepository.findByVideoKey(key);
        Integer like=0;
        Integer dislike=0;
        if (entityList.size()==0){
            return new LikeCountDTO(like,dislike);
        }

        for (VideoLikeEntity entity:entityList) {
            if (entity.getType().equals(LikeType.LIKE)){
                like++;
            }else if (entity.getType().equals(LikeType.DISLIKE)){
                dislike++;
            }
        }
        return new LikeCountDTO(like,dislike);
    }



    public VideoLikeDto toDTO(VideoLikeEntity entity){
        VideoLikeDto dto=new VideoLikeDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setVideoKey(entity.getVideoKey());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
