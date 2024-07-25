package com.lorettabank.service;

import com.lorettabank.dto.TransactionDTO;
import com.lorettabank.entity.Transaction;
import com.lorettabank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = Transaction.builder()
                .account(new Account(transactionDTO.getAccountId()))
                .type(transactionDTO.getType())
                .amount(transactionDTO.getAmount())
                .date(LocalDateTime.now())
                .description(transactionDTO.getDescription())
                .journalType(transactionDTO.getJournalType())
                .build();
        transactionRepository.save(transaction);
        return transactionDTO;
    }

    public TransactionDTO getTransaction(Long id, Long userId) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(id, userId);
        if (transactionOpt.isEmpty()) {
            throw new RuntimeException("Transaction not found");
        }
        Transaction transaction = transactionOpt.get();
        return TransactionDTO.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccount().getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .description(transaction.getDescription())
                .journalType(transaction.getJournalType())
                .build();
    }

    public List<TransactionDTO> getTransactions(Long userId) {
        List<Transaction> transactions = transactionRepository.findAll(userId);
        return transactions.stream()
                .map(transaction -> TransactionDTO.builder()
                        .id(transaction.getId())
                        .accountId(transaction.getAccount().getId())
                        .type(transaction.getType())
                        .amount(transaction.getAmount())
                        .date(transaction.getDate())
                        .description(transaction.getDescription())
                        .journalType(transaction.getJournalType())
                        .build())
                .toList();
    }

    public void deleteTransaction(Long id, Long userId) {
        transactionRepository.deleteById(id, userId);
    }
}
