package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.dto.model.UserDTO;
import org.project.ecommercebackend.dto.request.SigninResponseDTO;

public interface AuthService {

    UserDTO signUp(UserDTO userDTO);

    SigninResponseDTO logIn(UserDTO userDTO);
}
