package com.company.dto;

import com.company.enums.GeneralStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ChannelDto {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String description;

    private GeneralStatus status;
    private String key;


    private LocalDateTime createDate;
    private LocalDateTime updateDate;


    private String photoURL;
    private String bannerURL;
}
