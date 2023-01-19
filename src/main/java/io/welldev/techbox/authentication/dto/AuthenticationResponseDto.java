package io.welldev.techbox.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;



@AllArgsConstructor
@Getter
public class AuthenticationResponseDto {
    private  String accessToken;
    private String refreshToken;

}
