package com.lorettabank.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CardDTO {
    private Integer id;
    private Long userId;
    private Long accountId;
    private String cardNumber;
    private LocalDate expiryDate;
    private String cvv;
    private double creditLimit;
    private double balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
