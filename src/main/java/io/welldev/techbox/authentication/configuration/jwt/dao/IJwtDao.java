package io.welldev.techbox.authentication.configuration.jwt.dao;

import io.welldev.techbox.authentication.configuration.jwt.entity.Jwt;
import io.welldev.techbox.user.entity.User;

public interface IJwtDao {
    void saveTokenForUser(User user, String jwtAccessToken, String jwtRefreshToken);

    boolean isUserExist(int userId);

    void updateTokenForUser(User user, String jwtAccessToken);

    Jwt getTheRowOfJwt(int userId);
}
