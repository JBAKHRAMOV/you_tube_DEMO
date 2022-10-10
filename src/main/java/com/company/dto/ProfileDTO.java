package com.company.dto;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {

    private Integer id;
    @NotNull
    @Size(min = 3, max = 15, message = "name size min=3 , max=15")
    private String name;
    @NotNull
    @Size(min = 3, max = 15, message = "name size min=3 , max=15")
    private String surname;
    @Email
    private String email;
    @NotNull
    @Size(min = 3, max = 15, message = "name size min=3 , max=15")
    private String password;

    private ProfileStatus status;
    private ProfileRole role;

    private Integer photoId;
    private String photoURL;

    private String jwt;

    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate;

    private AttachDTO attachDto;
}
