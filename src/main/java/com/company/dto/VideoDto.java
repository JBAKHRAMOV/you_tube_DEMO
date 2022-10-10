package com.company.dto;

import com.company.enums.VideoStatus;
import com.company.enums.VideoType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VideoDto  {
    private Integer id;
    private UUID key;
    private String title;
    private String description;
    private VideoStatus status;
    private VideoType type;
    private Integer viewCount;
    private Integer sharedCount;
    private String previewAttachId;
    private Integer categoryId;
    private String videoId;
    private Integer channelId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private LikeCountDTO likeCountDTO;

    private String previewAttachURL;
    private String videoAttachURL;

    private CategoryDTO categoryDTO;
    private ChannelDto channelDto;
}
