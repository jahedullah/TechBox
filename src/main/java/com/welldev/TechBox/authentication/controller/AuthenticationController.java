package com.welldev.TechBox.authentication.controller;


import com.welldev.TechBox.authentication.dto.AuthenticationRequestDto;
import com.welldev.TechBox.authentication.dto.AuthenticationResponseDto;
import com.welldev.TechBox.authentication.service.IAuthenticationService;
import com.welldev.TechBox.constant.AUTH_URL;
import com.welldev.TechBox.user.dto.UserDto;
import com.welldev.TechBox.user.dto.UserRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authService;


    @PostMapping(AUTH_URL.USER_REGISTRATION)
    public ResponseEntity<UserDto> registerUser(
            @Valid @RequestBody UserRegisterRequestDto request
    ) throws IOException {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(AUTH_URL.ADMIN_REGISTRATION)
    public ResponseEntity<UserDto> registerAdmin(
            @Valid @RequestBody UserRegisterRequestDto request,
            HttpServletResponse response
    ) throws IOException {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(AUTH_URL.AUTHENTICATE)
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @Valid @RequestBody AuthenticationRequestDto request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.authenticate(request, response));
    }
}
