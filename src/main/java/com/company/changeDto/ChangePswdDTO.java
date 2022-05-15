package com.company.changeDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePswdDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
}
