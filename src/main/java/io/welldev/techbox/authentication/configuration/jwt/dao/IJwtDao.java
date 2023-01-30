package io.welldev.techbox.authentication.configuration.jwt.dao;

import io.welldev.techbox.authentication.configuration.jwt.entity.Jwt;
import io.welldev.techbox.user.entity.User;

public interface IJwtDao {
    void saveTokenForUser(User user, String jwtAccessToken, String jwtRefreshToken);

    String getUserEmail(String refreshToken);

    boolean isUserExist(int userId);

    void updateTokenForUser(User user, String jwtAccessToken, String jwtRefreshToken);

    void updateAccessTokenForUser(User user, String newAccessToken);

    Jwt getTheRowOfJwt(int userId);
}
