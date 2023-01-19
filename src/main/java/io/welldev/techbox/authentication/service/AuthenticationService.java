package io.welldev.techbox.authentication.service;


import io.welldev.techbox.authentication.configuration.jwt.dao.JwtDao;
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

import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final JwtDao jwtDao;

    public UserDto register(UserRegisterRequestDto request){
        User user = null;


        switch (request.getUsertype()) {
            case "user":
                user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobilenumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUsertype(request.getUsertype());
                user.setAppUserRole(AppUserRole.USER);

                break;
            case "admin":
                user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobilenumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUsertype(request.getUsertype());
                user.setAppUserRole(AppUserRole.ADMIN);

                break;
            case "superadmin":
                user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobilenumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUsertype(request.getUsertype());
                user.setAppUserRole(AppUserRole.SUPER_ADMIN);
                break;
        }

        userDao.save(user);
        return new UserDto(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getMobilenumber(),
                user.getUsertype());
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request, HttpServletResponse response) {
        var user = userDao.findByEmail(request.getEmail());
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        AuthenticationResponseDto authenticationResponseDto = jwtService.saveTokenForUser(user);
//        Cookie refreshCookie = new Cookie("refresh_token", jwtRefreshToken);
//        refreshCookie.setHttpOnly(true);
//
//        response.addCookie(refreshCookie);
        return authenticationResponseDto;

    }
}
