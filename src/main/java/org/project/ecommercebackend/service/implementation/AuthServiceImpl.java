package org.project.ecommercebackend.service.implementation;

import org.hibernate.query.IllegalQueryOperationException;
import org.project.ecommercebackend.constant.UserRole;
import org.project.ecommercebackend.dto.model.UserDTO;
import org.project.ecommercebackend.dto.request.SigninResponseDTO;
import org.project.ecommercebackend.mapper.UserMapper;
import org.project.ecommercebackend.model.User;
import org.project.ecommercebackend.service.service.AuthService;
import org.project.ecommercebackend.service.service.JWTService;
import org.project.ecommercebackend.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

//    @Autowired
    private final UserService userService;
//    @Autowired
    private final PasswordEncoder passwordEncoder;
//    @Autowired
    private final AuthenticationManager authenticationManager;
//    @Autowired
    private final JWTService jwtService;

    @Autowired
    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserDTO signUp(UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getRole() == null)
            userDTO.setRole(UserRole.USER);
        return userService.addUser(userDTO).orElseThrow(()->new IllegalQueryOperationException("User already exists"));
    }

    @Override
    public SigninResponseDTO logIn(UserDTO userDTO){
//        System.out.println("Reached logIn in auth service impl");
//
//        System.out.println(userDTO.getEmail() + ", " + userDTO.getPassword());

//        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getEmail(),
                            userDTO.getPassword()));
//        } catch (AuthenticationException e) {
//            System.out.println("Authentication failed: " + e.getMessage());
//        }

//        System.out.println("Reached logIn in auth service impl after authentication");

//        UserDTO userDTO = userService.getUserByEmail(userDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
//        User user = userService.getUserEntity();
//        System.out.println(user.getEmail());
        UserDTO user = userService.getUserByEmail(userDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
//        System.out.println("Reached logIn in auth service impl after user retrieval");
        var jwt = jwtService.generateToken(UserMapper.INSTANCE.toUser(user));

//        System.out.println("Reached logIn in auth service impl after jwt generation");

        return new SigninResponseDTO(jwt, user.getRole());
    }
}
