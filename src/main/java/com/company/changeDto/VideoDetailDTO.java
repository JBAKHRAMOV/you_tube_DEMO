package com.company.changeDto;

import lombok.Data;

import java.util.UUID;
@Data
public class VideoDetailDTO {
    //key, title, desc, categoryId, previewAttchId
    private String key;
    private String title;
    private Integer channelId;
    private String description;
    private Integer categoryId;
    private String previewAttchId;
    private String videoId;
}
