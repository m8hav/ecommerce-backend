package org.project.ecommercebackend.dto.request;

import org.project.ecommercebackend.constant.UserRole;

public class SigninResponseDTO {
    private final String token;
    private final UserRole role;

    public SigninResponseDTO(String token, UserRole role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public UserRole getRole() {
        return role;
    }
}
