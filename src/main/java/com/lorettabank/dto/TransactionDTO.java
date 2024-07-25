package com.lorettabank.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDTO {
    private Long id;
    private Long accountId;
    private String type;
    private double amount;
    private LocalDateTime date;
    private String description;
    private String journalType;
}
