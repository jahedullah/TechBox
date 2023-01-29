package io.welldev.techbox.authentication.configuration.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.welldev.techbox.authentication.configuration.jwt.service.JwtService;
import io.welldev.techbox.exceptionHandler.InvalidJwtAuthenticationException;
import io.welldev.techbox.exceptionHandler.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final CacheManager cacheManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, SignatureException {
        Cookie[] cookies = request.getCookies();
        String token = "";
        String accessToken = null;
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (!token.isEmpty()) {
            token = URLDecoder.decode(token, StandardCharsets.UTF_8.toString());
            String[] tokens = token.split(":");
            accessToken = tokens[0];
            refreshToken = tokens[1];
            // do something with the accessToken and refreshToken
        }
        // Cutting the "Bearer Token " String out of the token. Basically storing the actual token.
        final String jwt;

        // To Extract the user Email from the database.
        final String userEmail;

        // Checking if the authorizationHeader is empty or holding any token that does not start with Bearer.

        if (accessToken == null) {
            // The Request will be rejected.
            filterChain.doFilter(request, response);
            return;
        }
        try {
            // Because "Bearer Token " String character counts upto 13. So the real token appears from index 13.
            jwt = accessToken;
            // Check if the token is in the blacklist cache
            if (cacheManager.getCache("jwtBlacklistCache").get(jwt) != null)
                throw new InvalidJwtAuthenticationException("Token blacklisted");
            userEmail = jwtService.extractUsername(jwt);

            // if we have the userEmail and the user is not Authenticated Yet.
            if (userEmail != null
                    &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                //Check if the user is Valid or Not.
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    //if valid then create a token of type "UsernamePasswordAuthenticationToken"
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    authenticationToken.setDetails(
                            jwt
                    );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }

            }
        } catch (ExpiredJwtException ex) {
            response.setHeader("error", ex.getMessage());
            response.setStatus(FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("ERROR MESSAGE", ex.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);

        } catch (InvalidJwtAuthenticationException | SignatureException e) {
            response.setStatus(UNAUTHORIZED.value());
            ErrorResponse error = new ErrorResponse();
            error.setMessage(e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);

        }

        // pass the response to next filter-chain if there is any to make the api being executed and pass the data.

        filterChain.doFilter(request, response);


    }
}
