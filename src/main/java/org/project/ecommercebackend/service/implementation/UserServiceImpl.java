package org.project.ecommercebackend.service.implementation;

import org.project.ecommercebackend.dto.model.UserDTO;
import org.project.ecommercebackend.mapper.UserMapper;
import org.project.ecommercebackend.model.User;
import org.project.ecommercebackend.repository.UserRepository;
import org.project.ecommercebackend.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private boolean checkIfUserValid(UserDTO userDTO) {
        return userDTO != null
                && userDTO.getName() != null
                && userDTO.getEmail() != null
                && userDTO.getPassword() != null
                && userDTO.getAdmin() != null;
    }

    @Override
    public Optional<UserDTO> addUser(UserDTO userDTO) {
        if (!checkIfUserValid(userDTO)) {
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

    @Override
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId()).orElse(null);
        if(existingUser == null) {
            throw new IllegalArgumentException("User with this id does not exist");
        }
        User user = UserMapper.INSTANCE.toUser(
                UserMapper.INSTANCE.toUserDTO(existingUser).update(userDTO));
        User updatedUser = userRepository.save(user);
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
}