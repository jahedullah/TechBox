package io.welldev.techbox.authentication.controller;


import io.welldev.techbox.authentication.dto.AuthenticationRequestDto;
import io.welldev.techbox.authentication.dto.AuthenticationResponseDto;
import io.welldev.techbox.authentication.service.IAuthenticationService;
import io.welldev.techbox.constant.AUTH_URL;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.dto.UserRegisterRequestDto;
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
