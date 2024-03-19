package org.project.ecommercebackend.config;

import org.project.ecommercebackend.authFilter.JWTAuthFilter;
import org.project.ecommercebackend.constant.UserRole;
import org.project.ecommercebackend.service.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JWTAuthFilter jwtAuthFilter;
    private final UserService userService;
    public SecurityConfig(JWTAuthFilter jwtAuthFilter, UserService userService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/v1/auth/**"
                        )
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/v1/product/**",
                                "/v1/products/**"
                        )
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/v1/users/**"
                        )
                        .hasAnyAuthority(UserRole.ADMIN.name())
                        .requestMatchers(
                                HttpMethod.POST,
                                "/v1/products",
                                "/v1/products/**"
                        )
                        .hasAnyAuthority(UserRole.ADMIN.name())
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/v1/product/**"
                        )
                        .hasAnyAuthority(UserRole.ADMIN.name())
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/v1/product/**"
                        )
                        .hasAnyAuthority(UserRole.ADMIN.name())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
