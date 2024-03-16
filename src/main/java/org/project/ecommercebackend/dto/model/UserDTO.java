package org.project.ecommercebackend.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean admin;


    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, String password, Boolean admin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
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

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
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
        if (newUserDTO.getAdmin() != null) {
            this.setAdmin(newUserDTO.getAdmin());
        }
        return this;
    }
}
