package org.project.ecommercebackend.service.implementation;

import org.project.ecommercebackend.dto.model.UserDTO;
import org.project.ecommercebackend.mapper.UserMapper;
import org.project.ecommercebackend.model.User;
import org.project.ecommercebackend.repository.UserRepository;
import org.project.ecommercebackend.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    private final AuthService

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private boolean isUserInvalid(UserDTO userDTO) {
        return userDTO == null
                || userDTO.getName() == null
                || userDTO.getEmail() == null
                || userDTO.getPassword() == null
                || userDTO.getRole() == null;
    }

    @Override
    public Optional<UserDTO> addUser(UserDTO userDTO) {
        if (isUserInvalid(userDTO)) {
            throw new IllegalArgumentException("All user fields must be provided");
        }
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        User user = UserMapper.INSTANCE.toUser(userDTO);
        User savedUser = userRepository.save(user);
        return Optional.ofNullable(UserMapper.INSTANCE.toUserDTO(savedUser));
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.INSTANCE.toUserDTOList(users);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper.INSTANCE::toUserDTO);
    }

//    @Override
//    private Optional<UserDTO> getUser() {
//        return getUserByEmail(getAuthenticatedUserEmail());
//    }

    @Override
    public UserDTO getUserSafe() {
        User user = getUserEntity();
        user.setId(null);
        user.setPassword(null);
        return UserMapper.INSTANCE.toUserDTO(user);
    }

    @Override
    public User getUserEntity() {
        return userRepository.findByEmail(getAuthenticatedUserEmail());
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        return user.map(UserMapper.INSTANCE::toUserDTO);
    }

    @Override
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        if (isUserInvalid(userDTO)) {
            throw new IllegalArgumentException("All user fields must be provided");
        }
        UserDTO existingUserDTO = getUserByEmail(getAuthenticatedUserEmail()).orElse(null);
        existingUserDTO.update(userDTO);
        User updatedUser = userRepository.save(UserMapper.INSTANCE.toUser(existingUserDTO));
        return Optional.ofNullable(UserMapper.INSTANCE.toUserDTO(updatedUser));
    }

    @Override
    public boolean deleteUser(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        throw new IllegalArgumentException("User with this id does not exist");
    }

    @Override
    public boolean deleteUser() {
        System.out.println("Reached deleteUser in user service impl");
        System.out.println(getAuthenticatedUserEmail());
        return deleteUser(getUserByEmail(getAuthenticatedUserEmail()).orElse(null).getId());
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            UserDetails userDetails = userRepository.findByEmail(username);
            if(userDetails == null) {
                throw new UsernameNotFoundException("User Not Found");
            }
            return userDetails;
        };
    }
}