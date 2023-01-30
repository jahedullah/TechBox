package io.welldev.techbox.authentication.configuration.securityConfiguration;


import io.welldev.techbox.authentication.configuration.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
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
import static io.welldev.techbox.roleAndPermission.AppUserRole.SUPER_ADMIN;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(POST, "/login").permitAll()
                .antMatchers(GET, "/users").permitAll()
                .antMatchers(POST, "/users").permitAll()
                .antMatchers("/users/{userId}").permitAll()
                .antMatchers(GET, "/products").permitAll()
                .antMatchers(GET, "/products/**").permitAll()
                .antMatchers(PUT, "/refreshtoken").permitAll()


                .antMatchers(DELETE, "/admin/**").hasAnyRole(ADMIN.name(), SUPER_ADMIN.name())
                .antMatchers(POST, "/register/admin").hasRole(SUPER_ADMIN.name())
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

}
