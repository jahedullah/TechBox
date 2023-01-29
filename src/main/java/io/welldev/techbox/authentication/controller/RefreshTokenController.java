package io.welldev.techbox.authentication.controller;

import io.welldev.techbox.authentication.configuration.jwt.service.JwtService;
import io.welldev.techbox.authentication.dto.AccessTokenDto;
import io.welldev.techbox.authentication.dto.RefreshTokenDto;
import io.welldev.techbox.exceptionHandler.InvalidJwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RefreshTokenController {
    private final JwtService jwtService;

    @PutMapping("/refreshtoken")
    public ResponseEntity<AccessTokenDto> getAccessToken(@RequestBody RefreshTokenDto refreshTokenDto,
                                                         HttpServletResponse response){
        AccessTokenDto accessTokenDto = null;
        accessTokenDto = jwtService.newAccessToken(refreshTokenDto, response);


        return ResponseEntity.status(HttpStatus.CREATED).body(accessTokenDto);
    }
}
