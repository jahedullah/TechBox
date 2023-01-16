package com.welldev.TechBox.model.service;


import com.welldev.TechBox.model.dto.AuthenticationDto.AuthenticationRequestDto;
import com.welldev.TechBox.model.dto.AuthenticationDto.AuthenticationResponseDto;
import com.welldev.TechBox.model.dto.UserDto.UserRegisterRequestDto;
import com.welldev.TechBox.model.dto.UserDto.UserDto;

public interface AuthenticationService {
    UserDto register(UserRegisterRequestDto request);
    AuthenticationResponseDto authenticate(AuthenticationRequestDto request);
}
