package com.company.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
public class AuthDTO {
    @Email(message = "email required")
    private String email;
    @NotBlank(message = "Password required")
    @Size(min = 4, max = 15, message = "password errors")
    private String password;
}
