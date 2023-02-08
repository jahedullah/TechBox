package io.welldev.techbox.authentication.configuration.securityConfiguration;


import io.welldev.techbox.authentication.configuration.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static io.welldev.techbox.roleAndPermission.AppUserPermission.PRODUCT_WRITE;
import static io.welldev.techbox.roleAndPermission.AppUserRole.ADMIN;
import static org.springframework.http.HttpMethod.*;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(getCorsConfigurationSource())
                .and()

                .authorizeRequests()
                .antMatchers(POST, "/login").permitAll()
                .antMatchers(GET, "/users").permitAll()
                .antMatchers(POST, "/users").permitAll()
                .antMatchers("/users/{userId}").permitAll()
                .antMatchers(GET, "/products").permitAll()
                .antMatchers(GET, "/products/**").permitAll()
                .antMatchers(PUT, "/refreshtoken").permitAll()


                .antMatchers(DELETE, "/admin/**").hasAnyRole(ADMIN.name())
                .antMatchers(DELETE, "/products/**").hasAuthority(PRODUCT_WRITE.getPermission())
                .antMatchers(PUT, "/products/**").hasAuthority(PRODUCT_WRITE.getPermission())
                .antMatchers(PATCH, "/products/**").hasAuthority(PRODUCT_WRITE.getPermission())
                .antMatchers(POST, "/products/**").hasAuthority(PRODUCT_WRITE.getPermission())


                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);


        http.csrf().disable();

        return http.build();


    }
    public CorsConfigurationSource getCorsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.addAllowedOrigin("http://localhost:4200");
                config.setAllowCredentials(true);
                return config;
            }
        };
    }


}
