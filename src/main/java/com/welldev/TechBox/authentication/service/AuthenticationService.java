package com.welldev.TechBox.authentication.service;


import com.welldev.TechBox.authentication.dto.AuthenticationRequestDto;
import com.welldev.TechBox.authentication.dto.AuthenticationResponseDto;
import com.welldev.TechBox.user.dto.UserDto;
import com.welldev.TechBox.user.dto.UserRegisterRequestDto;

import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    UserDto register(UserRegisterRequestDto request);
    AuthenticationResponseDto authenticate(AuthenticationRequestDto request, HttpServletResponse response);
}
