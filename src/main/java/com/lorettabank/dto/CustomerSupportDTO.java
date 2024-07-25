package com.lorettabank.dto;

import com.lorettabank.entity.SupportStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CustomerSupportDTO {
    private Integer id;
    private Long userId;
    private String query;
    private String response;
    private SupportStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO user; // Nested DTO for user details
}
