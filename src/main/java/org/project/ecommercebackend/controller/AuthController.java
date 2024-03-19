package org.project.ecommercebackend.controller;

import org.project.ecommercebackend.dto.model.UserDTO;
import org.project.ecommercebackend.dto.request.SigninResponseDTO;
import org.project.ecommercebackend.service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public UserDTO signUp(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User info is required");
        }
        if (userDTO.getId() != null) {
            throw new IllegalArgumentException("User id must be null");
        }
        return authService.signUp(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<SigninResponseDTO> logIn(@RequestBody UserDTO userDTO){
//        System.out.println("Reached logIn in auth controller");
        return ResponseEntity.ok(authService.logIn(userDTO));
    }
}
