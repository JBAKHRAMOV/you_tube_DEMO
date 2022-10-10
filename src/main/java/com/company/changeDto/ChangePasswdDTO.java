package com.company.changeDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswdDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
}
