package org.project.ecommercebackend.dto.model;

import org.project.ecommercebackend.constant.UserRole;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserDTO update(UserDTO newUserDTO) {
        if (newUserDTO.getName() != null) {
            this.setName(newUserDTO.getName());
        }
        if (newUserDTO.getEmail() != null) {
            this.setEmail(newUserDTO.getEmail());
        }
        if (newUserDTO.getPassword() != null) {
            this.setPassword(newUserDTO.getPassword());
        }
        if (newUserDTO.getRole() != null) {
            this.setRole(newUserDTO.getRole());
        }
        return this;
    }
}
