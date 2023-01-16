package com.welldev.TechBox.model.dto.UserDto;

import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    private String firstname;
    private String lastname;
    private String email;
    private String mobilenumber;
}
