package com.welldev.TechBox.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String mobilenumber;
    private String usertype;
}
