package org.project.ecommercebackend.authFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.ecommercebackend.service.service.JWTService;
import org.project.ecommercebackend.service.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserService userService;

    public JWTAuthFilter(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Reached doFilterInternal in JWTAuthFilter");
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer")){
            System.out.println("Reached doFilterInternal in JWTAuthFilter after header check success");
            filterChain.doFilter(request,response);
            return;
        }

        System.out.println("Reached doFilterInternal in JWTAuthFilter after header check failed");

        jwt = authHeader.substring(7);
        userEmail=jwtService.extractUserName(jwt);

        if(!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null ){

            System.out.println("Reached doFilterInternal in JWTAuthFilter after userEmail check success");

            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt,userDetails)){

                System.out.println("Reached doFilterInternal in JWTAuthFilter after token check success");

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        System.out.println("Reached doFilterInternal in JWTAuthFilter before filter chain");
        filterChain.doFilter(request,response);
    }
}
