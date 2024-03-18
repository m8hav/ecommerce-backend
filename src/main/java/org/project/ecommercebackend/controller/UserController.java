package org.project.ecommercebackend.controller;


import org.project.ecommercebackend.dto.model.UserDTO;
import org.project.ecommercebackend.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.MissingFormatArgumentException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

//    @GetMapping("/user/{id}")
//    public UserDTO getUserById(@PathVariable Long id) {
//        return userService.getUserById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
//    }
    @GetMapping("/user")
    public UserDTO getUser() {
        return userService.getUserSafe();
    }

    @PutMapping("/user")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO).orElseThrow(() -> new MissingFormatArgumentException("User not updated"));
    }

//    @DeleteMapping("/user/{id}")
//    public boolean deleteUser(@PathVariable Long id) {
//        return userService.deleteUser(id);
//    }
    @DeleteMapping("/user")
    public boolean deleteUser() {
        return userService.deleteUser();
    }
}
