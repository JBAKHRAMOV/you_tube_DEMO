package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private String id;
    private String path;
    private String extension;
    private String origenName;
    private Long size;
    private LocalDateTime createdDate;

    private String url;

    public AttachDTO() {

    }
}
