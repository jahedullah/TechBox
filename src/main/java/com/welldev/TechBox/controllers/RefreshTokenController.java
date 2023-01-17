package com.welldev.TechBox.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

//    private final HttpSession session;
//
//    @PostMapping("/refresh")
//    public String refresh(@RequestBody RefreshTokenRequest request) {
//        // Validate the refresh token
//        // ...
//
//        // Generate a new refresh token
//        String newRefreshToken = // ...
//
//                // Store the new refresh token in the session
////                session.setAttribute("refresh_token");
//
//        // Set the session cookie to be HTTP-only
//        session.setAttribute("SESSION_COOKIE_HTTP_ONLY", true);
//
//        return newRefreshToken;
//    }
}
