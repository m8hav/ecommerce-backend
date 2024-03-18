package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.dto.model.UserDTO;
import org.project.ecommercebackend.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public interface UserService {
    String getAuthenticatedUserEmail();

    Optional<UserDTO> addUser(UserDTO userDTO);

    List<UserDTO> getUsers();

    Optional<UserDTO> getUserById(Long id);

    UserDTO getUserSafe();

    User getUserEntity();

    Optional<UserDTO> getUserByEmail(String email);

    Optional<UserDTO> updateUser(UserDTO userDTO);

    Optional<UserDTO> updateUserPassword(String password);

    boolean deleteUser(Long id);

    boolean deleteUser();

    UserDetailsService userDetailsService();
}
