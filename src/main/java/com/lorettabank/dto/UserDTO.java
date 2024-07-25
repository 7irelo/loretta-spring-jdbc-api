package com.lorettabank.dto;

import com.lorettabank.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate dateOfBirth;
    private String occupation;
    private String phone;
    private String email;
    private String username;
    private String password; // Password is typically not included in DTOs for security reasons

    // Convert from entity
    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .occupation(user.getOccupation())
                .phone(user.getPhone())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
