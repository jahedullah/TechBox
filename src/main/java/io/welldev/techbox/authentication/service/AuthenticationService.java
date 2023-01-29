package io.welldev.techbox.authentication.service;


import io.welldev.techbox.authentication.configuration.jwt.service.JwtService;
import io.welldev.techbox.authentication.dto.AuthenticationRequestDto;
import io.welldev.techbox.authentication.dto.AuthenticationResponseDto;
import io.welldev.techbox.user.dao.IUserDao;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.dto.UserRegisterRequestDto;
import io.welldev.techbox.user.entity.User;
import io.welldev.techbox.roleAndPermission.AppUserRole;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Transactional
    public UserDto register(UserRegisterRequestDto request) {
        User user = null;
        switch (request.getUsertype()) {
            case "admin":
                user = new User();
                user.setFirstName(request.getFirstname());
                user.setLastName(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobileNumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUserType(request.getUsertype());
                user.setAppUserRole(AppUserRole.ADMIN);

                break;
            case "superadmin":
                user = new User();
                user.setFirstName(request.getFirstname());
                user.setLastName(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobileNumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUserType(request.getUsertype());
                user.setAppUserRole(AppUserRole.SUPER_ADMIN);

                break;
            default:
                user = new User();
                user.setFirstName(request.getFirstname());
                user.setLastName(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobileNumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUserType(request.getUsertype());
                user.setAppUserRole(AppUserRole.USER);
                break;
        }

        userDao.save(user);
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getUserType());
    }

    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request, HttpServletResponse response) {
        var user = userDao.findByEmail(request.getEmail());
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        AuthenticationResponseDto authenticationResponseDto = jwtService.saveTokenForUser(user);
        String accessToken = authenticationResponseDto.getAccessToken();
        String refreshToken = authenticationResponseDto.getRefreshToken();
        String token = accessToken + ":" + refreshToken;
        Cookie jwtCookie = new Cookie("token", token);
        response.addCookie(jwtCookie);
        return authenticationResponseDto;

//        String accessToken = authenticationResponseDto.getAccessToken();
//        String refreshToken = authenticationResponseDto.getRefreshToken();
//        String tokens = accessToken + ":" + refreshToken;
//        Cookie cookie = new Cookie("tokens", tokens);
//        cookie.setMaxAge(60 * 60 * 24); // expires in 24 hours
//        response.addCookie(cookie);

    }
}
