package com.welldev.TechBox.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;



@AllArgsConstructor
@Getter
public class AuthenticationResponseDto {
    private  String accessToken;
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String mobilenumber;
    private String usertype;

}
