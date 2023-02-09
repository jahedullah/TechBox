package io.welldev.techbox.authentication.controller;


import io.welldev.techbox.authentication.dto.AuthenticationRequestDto;
import io.welldev.techbox.authentication.dto.AuthenticationResponseDto;
import io.welldev.techbox.authentication.service.IAuthenticationService;
import io.welldev.techbox.constant.AUTH_URL;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.dto.UserRegisterRequestDto;
import io.welldev.techbox.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authService;
    private final CacheManager cacheManager;

    private final UserDetailsService userDetailsService;


    @PostMapping(AUTH_URL.USER_REGISTRATION)
    public ResponseEntity<UserDto> registerUser(
            @Valid @RequestBody UserRegisterRequestDto request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }


    @PostMapping(AUTH_URL.AUTHENTICATE)
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @Valid @RequestBody AuthenticationRequestDto request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.authenticate(request, response));
    }


    @PostMapping(AUTH_URL.USER_LOGOUT)
    public ResponseEntity<?> logout() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        // Remove the token from the cache
        cacheManager.getCache("jwtBlacklistCache").put(token, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping(AUTH_URL.CURRENT_USER)
    public UserDto getCurrentUser(Principal principal){
        Optional<User> user = Optional.ofNullable((User) userDetailsService.loadUserByUsername(principal.getName()));
        return user.map(value -> new UserDto(
                value.getId(),
                value.getFirstName(),
                value.getLastName(),
                value.getEmail(),
                value.getMobileNumber(),
                value.getUserType())).orElse(null);
    }
}
