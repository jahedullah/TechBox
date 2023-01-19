package io.welldev.techbox.authentication.service;


import io.welldev.techbox.authentication.dto.AuthenticationRequestDto;
import io.welldev.techbox.authentication.dto.AuthenticationResponseDto;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.dto.UserRegisterRequestDto;

import javax.servlet.http.HttpServletResponse;

public interface IAuthenticationService {
    UserDto register(UserRegisterRequestDto request);
    AuthenticationResponseDto authenticate(AuthenticationRequestDto request, HttpServletResponse response);
}
