package com.lorettabank.service;

import com.lorettabank.dto.UserDTO;
import com.lorettabank.entity.User;
import com.lorettabank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO registerUser(UserDTO userDTO) {
        String id = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        User user = User.builder()
                .id(id)
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .occupation(userDTO.getOccupation())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(encodedPassword)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return userDTO;
    }

    public UserDTO loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .occupation(user.getOccupation())
                .phone(user.getPhone())
                .username(user.getUsername())
                .build();
    }

    public UserDTO getCurrentUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .occupation(user.getOccupation())
                .phone(user.getPhone())
                .username(user.getUsername())
                .build();
    }

    public UserDTO updateUser(UserDTO userDTO) {
        User user = User.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .occupation(userDTO.getOccupation())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword()) // consider hashing password if updated
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.update(user);
        return userDTO;
    }
}
