package com.company.changeDto;

import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ChannelStatusDTO {
    @NotNull
    private GeneralStatus status;
    @NotNull
    private String key;
}
