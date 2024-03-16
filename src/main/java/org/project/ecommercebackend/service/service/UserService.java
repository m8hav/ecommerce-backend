package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.dto.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    Optional<UserDTO> addUser(UserDTO userDTO);

    List<UserDTO> getUsers();

    Optional<UserDTO> getUserById(Long id);

    Optional<UserDTO> updateUser(UserDTO userDTO);

    boolean deleteUser(Long id);
}
