package com.welldev.TechBox.model.dto.AuthenticationDto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class AuthenticationRequestDto {


    private String email;

    private String password;

}
