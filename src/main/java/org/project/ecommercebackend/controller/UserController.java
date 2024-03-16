package org.project.ecommercebackend.controller;


import org.project.ecommercebackend.dto.model.UserDTO;
import org.project.ecommercebackend.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.MissingFormatArgumentException;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User info is required");
        }
        if (userDTO.getId() != null) {
            throw new IllegalArgumentException("User id must be null");
        }
        return userService.addUser(userDTO).orElseThrow(() -> new MissingFormatArgumentException("User not saved"));
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @PutMapping("/user")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO).orElseThrow(() -> new MissingFormatArgumentException("User not updated"));
    }

    @DeleteMapping("/user/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
