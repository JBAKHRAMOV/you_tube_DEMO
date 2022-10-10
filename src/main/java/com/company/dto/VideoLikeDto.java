package com.company.dto;

import com.company.enums.LikeType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class VideoLikeDto {
    private Integer id;
    @NotNull
    private String videoKey;
    @NotNull
    private LikeType type;
    private LocalDateTime createdDate;
}
