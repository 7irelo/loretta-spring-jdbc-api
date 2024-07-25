package com.lorettabank.dto;

import com.lorettabank.entity.LoanType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class LoanDTO {
    private Long id;
    private Long userId;
    private Long accountId;
    private LoanType loanType;
    private double amount;
    private double interestRate;
    private int term;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
