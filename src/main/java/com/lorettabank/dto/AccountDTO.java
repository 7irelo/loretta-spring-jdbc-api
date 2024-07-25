package com.lorettabank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Integer id;
    private String accountNumber;
    private String name;
    private String userId;
    private String accountType;
    private double availableBalance;
    private double latestBalance;
    private String accountStatus;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TransactionDTO> transactions;
    private List<CardDTO> cards;
    private List<LoanDTO> loans;
    private UserDTO user;
}
