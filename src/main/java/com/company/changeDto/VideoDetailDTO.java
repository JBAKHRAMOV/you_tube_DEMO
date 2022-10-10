package com.company.changeDto;

import lombok.Data;

@Data
public class VideoDetailDTO {
    private String key;
    private String title;
    private Integer channelId;
    private String description;
    private Integer categoryId;
    private String previewAttachId;
    private String videoId;
}
