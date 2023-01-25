package io.welldev.techbox.authentication.configuration.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.welldev.techbox.authentication.configuration.jwt.dao.JwtDao;
import io.welldev.techbox.authentication.configuration.jwt.entity.Jwt;
import io.welldev.techbox.authentication.dto.AccessTokenDto;
import io.welldev.techbox.authentication.dto.AuthenticationResponseDto;
import io.welldev.techbox.user.dao.UserDao;
import io.welldev.techbox.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtDao jwtDao;
    private final UserDao userDao;

    private static final String SECRET_KEY = "58703273357638782F413F4428472B4B6250655368566D597133743677397A24";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    public String generateAccessToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // token is valid for 10 minutes. It is converted into MilliSeconds.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    public String generateRefreshToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // token is valid for 1 day. It is converted into MilliSeconds.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token,
                                UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    // This method is used to extract the JSON Web Token.
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // get the SignIn Key.
                .build()
                .parseClaimsJws(token)
                .getBody(); // Get all the claims from the token body.

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // decode the key in BASE64
        return Keys.hmacShaKeyFor(keyBytes); // run through the algorithm to extract.
    }

    @Transactional
    public AuthenticationResponseDto saveTokenForUser(User user) {
        boolean exist = jwtDao.isUserExist(user.getId());
        var jwtAccessToken = generateAccessToken(user);
        if (exist) {
            jwtDao.updateTokenForUser(user, jwtAccessToken);
            Jwt jwtRow = jwtDao.getTheRowOfJwt(user.getId());
            return new AuthenticationResponseDto(
                    jwtRow.getAccessToken(),
                    jwtRow.getRefreshToken()
            );

        } else {
            var jwtRefreshToken = generateRefreshToken(user);

            jwtDao.saveTokenForUser(user, jwtAccessToken, jwtRefreshToken);

            return new AuthenticationResponseDto(
                    jwtAccessToken,
                    jwtRefreshToken
            );

        }
    }

    @Transactional
    public AccessTokenDto newAccessToken(String refreshToken) {
        String userEmail = extractUsername(refreshToken);
        User user = userDao.findByEmail(userEmail);
        String newAccessToken = generateAccessToken(user);
        jwtDao.updateTokenForUser(user, newAccessToken);

        return new AccessTokenDto(
                newAccessToken
        );


    }

}

